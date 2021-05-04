package com.jepprot.submission2.userInterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jepprot.submission2.R
import com.jepprot.submission2.repository.DataUser
import com.jepprot.submission2.databinding.ActivityMainBinding
import com.jepprot.submission2.model.ListUserAdapter
import com.jepprot.submission2.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User"
        binding.rvGithubSearch.setHasFixedSize(true)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()


        binding.rvGithubSearch.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvGithubSearch.adapter = adapter

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemCallback(data: DataUser) {
                showSelectedUser(data)
                val moveDetail = Intent(this@MainActivity, DetailActivity::class.java)
                    moveDetail.putExtra(DetailActivity.EXTRA_DETAIl, data.login)
                startActivity(moveDetail)
            }
        })


        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)


        binding.btnSearch.setOnClickListener {
            search()
        }

        binding.edtUserSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                search()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        userViewModel.searchUser().observe(this, {
            if (it!=null) {
                adapter.rvList(it)
                bufferingState(false)
            }
        })
    }

    private fun showSelectedUser(user: DataUser) {
        Toast.makeText(this, "You Choose ${user.login}", Toast.LENGTH_SHORT).show()
    }

    private fun search() {
        bufferingState(true)
        val dataSearch = binding.edtUserSearch.text.toString()
        if (dataSearch.isEmpty())
            return bufferingState(true)
        userViewModel.setUser(dataSearch)
    }


    private fun bufferingState(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_localization, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_local) {
            val localIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(localIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.jepprot.submission2.userInterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jepprot.submission2.databinding.ActivityDetailBinding
import com.jepprot.submission2.model.SectionPagerAdapter
import com.jepprot.submission2.viewmodel.UserViewModel
import com.bumptech.glide.Glide


class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIl = "extra_detail"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val user = intent.getStringExtra(EXTRA_DETAIl)
        val bundleData = Bundle()
        bundleData.putString(EXTRA_DETAIl, user)

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        if (user != null) {
            detailViewModel.setDetailGithub(user)
        }
        detailViewModel.getDetailGithub().observe(this, {
            if (it != null) {
                binding.apply {
                    txtUsername.text = it.login
                    txtName.text = it.name
                    txtReposit.text = it.public_repos.toString()
                    txtFollowers.text = it.followers.toString()
                    txtFollowing.text = it.following.toString()
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(imgUserDetail)
                }
            }
        })
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundleData)
        binding.apply {
            viewPag.adapter = sectionPagerAdapter
            tab.setupWithViewPager(viewPag)
        }
    }
}
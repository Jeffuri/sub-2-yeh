package com.jepprot.submission2.userInterface.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jepprot.submission2.R
import com.jepprot.submission2.databinding.FragmentFollowersBinding
import com.jepprot.submission2.model.ListUserAdapter
import com.jepprot.submission2.userInterface.DetailActivity
import com.jepprot.submission2.viewmodel.FollowersViewModel


class FollowersFragment : Fragment() {

    private var bindingFragment: FragmentFollowersBinding? = null
    private val binding get() = bindingFragment!!

    private lateinit var user: String

    private lateinit var adater: ListUserAdapter
    private lateinit var viewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        user = args?.getString(DetailActivity.EXTRA_DETAIl).toString()
        bindingFragment = FragmentFollowersBinding.bind(view)

        adater = ListUserAdapter()
        adater.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adater
        }

        bufferingState(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        viewModel.setFollowers(user)
        viewModel.getFollowersTab().observe(viewLifecycleOwner, {
            if (it != null) {
                adater.rvList(it)
                bufferingState(false)
            }
        })
    }


    private fun bufferingState(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingFragment = null
    }

}
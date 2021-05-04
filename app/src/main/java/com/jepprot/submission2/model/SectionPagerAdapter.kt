package com.jepprot.submission2.model

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jepprot.submission2.R
import com.jepprot.submission2.userInterface.fragment.FollowersFragment
import com.jepprot.submission2.userInterface.fragment.FollowingFragment

class SectionPagerAdapter(private val state: Context, fragment: FragmentManager, bundle: Bundle): FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private lateinit var fragmentBundle: Bundle
    init {
        fragmentBundle = bundle
    }

    override fun getItem(position: Int): Fragment {
        var fragmentTab: Fragment? = null
        when(position){
            0 -> fragmentTab = FollowersFragment()
            1 -> fragmentTab = FollowingFragment()
        }
        fragmentTab?.arguments = this.fragmentBundle
        return fragmentTab as Fragment
    }

    @StringRes
    private val TAB_CHOOSE = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    override fun getCount(): Int {
        return 2
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return state.resources.getString(TAB_CHOOSE[position])
    }
}
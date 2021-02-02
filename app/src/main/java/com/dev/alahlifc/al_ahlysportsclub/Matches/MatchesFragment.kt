package com.dev.alahlifc.al_ahlysportsclub.Matches


import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.Matches.Future.FutureMatchesFragment
import com.dev.alahlifc.al_ahlysportsclub.Matches.Past.PastMatchesFragment

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentMatchesBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.android.material.tabs.TabLayoutMediator
import java.io.IOException


class MatchesFragment : Fragment() {

    private lateinit var binding : FragmentMatchesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_matches, container, false)
        return binding.root
    }

    private fun setupTabsAndViewPager() {
        binding.viewpagerSlider.adapter = NewsFragmentStateAdapter(this)
        TabLayoutMediator( binding.tabs,  binding.viewpagerSlider) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.next_matches)
                1 -> getString(R.string.past_matches)
                else -> null
            }
        }.attach()
    }

    inner class NewsFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun createFragment(position: Int) = when (position) {
            0 -> FutureMatchesFragment()
            1 -> PastMatchesFragment()
            else -> throw IllegalArgumentException("Position $position exceeds NewsFragmentStateAdapter count")
        }

        override fun getItemCount() = 2
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabsAndViewPager()
    }





    private fun changeTabsFonts() {
     // Util.changeTabsFont(context!!, binding.tabs)
    }








}

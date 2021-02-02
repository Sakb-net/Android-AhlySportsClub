package com.dev.alahlifc.al_ahlysportsclub.AboutClub

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.alahlifc.al_ahlysportsclub.Base.TabLayoutMediator
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentAboutClubBinding
import com.google.android.material.tabs.TabLayout
import org.jetbrains.annotations.NotNull
import java.io.IOException

class AboutClubFragment : Fragment() {

    private lateinit var binding : FragmentAboutClubBinding
    private lateinit var aboutClubSliderAdapter: AboutClubSliderAdapter
    private lateinit var viewModel: AboutClubViewModel
    private lateinit var dialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_about_club, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var mutableList  = mutableListOf<Pair<String,String>>()
        viewModel = ViewModelProviders.of(this).get(AboutClubViewModel::class.java)

        viewModel.about()
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                dialog = context?.showLoadingDialog()!!
                            else
                                dialog.dismiss()
                        }
                        is Resource.Success -> {
                            mutableList.add(Pair(data.data.title_one,data.data.content_one))
                            mutableList.add(Pair(data.data.title_two,data.data.content_two))
                           // aboutClubSliderAdapter.notifyDataSetChanged()
                            aboutClubSliderAdapter = AboutClubSliderAdapter(mutableList)
                            if (binding.viewpagerSlider.adapter == null)
                                binding.viewpagerSlider.adapter =aboutClubSliderAdapter



                            val tabLayoutMediator =
                                TabLayoutMediator( binding.indicator, binding.viewpagerSlider, true,
                                    object : TabLayoutMediator.OnConfigureTabCallback {
                                        override fun onConfigureTab(@NotNull tab: TabLayout.Tab, position: Int) {
                                            // position of the current tab and that tab
                                        }
                                    })

                            tabLayoutMediator.attach()


                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context?.toast(getString(R.string.need_internet))
                            } else {
                                context?.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })




        //  changeViewsFonts()
    }



}

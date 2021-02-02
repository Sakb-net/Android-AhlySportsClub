package com.dev.alahlifc.al_ahlysportsclub.Audience


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentAudienceBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException


class AudienceFragment : Fragment() {

    private lateinit var binding : FragmentAudienceBinding
    private lateinit var viewModel: AudienceViewModel
    private lateinit var adapter: AudienceAdapter
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_audience, container, false)
        changeViewsFonts()
        return binding.root
    }

    private fun changeViewsFonts() {

        Util.changeViewTypeFace(context, Constants.FONT_REGULAR, binding.questionTv)



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AudienceViewModel::class.java)
        getObservableData()
    }


    private fun getObservableData() {
        val lang = PrefManager.getLanguage()

        viewModel.audience(lang!!)
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

                            binding.questionTv.text = data.data.name

                            adapter = AudienceAdapter(data.data.anwsers)
                            if (binding.audienceRv.adapter == null)
                                binding.audienceRv.adapter = adapter


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
    }

}

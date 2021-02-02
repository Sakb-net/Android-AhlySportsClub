package com.dev.alahlifc.al_ahlysportsclub.Matches.Future


import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentFutureMatchesBinding
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import timber.log.Timber
import java.io.IOException


class FutureMatchesFragment : Fragment() {

    private lateinit var binding : FragmentFutureMatchesBinding
    private lateinit var viewModel: MatchesViewModel
    private lateinit var dialog: Dialog
    private var adapter :MatchesRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_future_matches, container, false)
       // binding.matchesRv.setHasFixedSize(true)
        adapter = MatchesRecyclerAdapter()
        binding.matchesRv.setHasFixedSize(true)
        binding.matchesRv.layoutManager = LinearLayoutManager(context)
        binding.matchesRv.adapter = adapter

        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchesViewModel::class.java)
        getObservableData()


    }

    private fun getObservableData() {
        val lang = PrefManager.getLanguage()
        viewModel.NexttMatches(lang!!,"10","0","next")
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

                            adapter!!.setData(data.data)

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

package com.dev.alahlifc.al_ahlysportsclub.Matches.Past


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.MatchDetails.MatchDetailsActivity
import com.dev.alahlifc.al_ahlysportsclub.Matches.Future.MatchesRecyclerAdapter

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentPastMatchesBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException


class PastMatchesFragment : Fragment() {

    private lateinit var binding : FragmentPastMatchesBinding
    private lateinit var viewModel: MatchesPastViewModel
    private lateinit var dialog: Dialog
    private var adapter : PastMatchesRecyclerAdapter? = null


    private var _hasLoadedOnce = false // your boolean field
  //  private var  mutableList  = mutableListOf<String>()


   /***
    *  override fun setUserVisibleHint(isFragmentVisible_: Boolean) {
        super.setUserVisibleHint(isFragmentVisible_)

        //  context?.toast("visible "+ isFragmentVisible_)
        if (isFragmentVisible_ && !_hasLoadedOnce) {
            getObservableData()

        }
    }*/



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_past_matches, container, false)
       adapter = PastMatchesRecyclerAdapter()
       binding.matchesRv.setHasFixedSize(true)
      // binding.matchesRv.setItemViewCacheSize(10)
       binding.matchesRv.layoutManager = LinearLayoutManager(context)

       adapter!!.setHasStableIds(true)

       binding.matchesRv.adapter = adapter
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MatchesPastViewModel::class.java)
         getObservableData()
    }



    private fun getObservableData() {
        val lang = PrefManager.getLanguage()
        viewModel.PastMatches(lang!!,"10","0","prev")
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
                            _hasLoadedOnce = true


                            adapter!!.setData(data!!.data)

                            adapter!!.onItemClick = {
                                context?.startActivity(
                                    Intent(context,MatchDetailsActivity::class.java)
                                    .putExtra("obj",data.data[it]!!))

                            }

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

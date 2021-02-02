package com.dev.alahlifc.al_ahlysportsclub.News


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.BigImage.BigImageActivity
import com.dev.alahlifc.al_ahlysportsclub.NewsDetails.NewsDetailsActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentNewsBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class NewsFragment : Fragment() {

    private lateinit var binding : FragmentNewsBinding
   //// private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private var dialog: Dialog ?= null
    private lateinit var adapter : NewsPagingAdapter
    private  var snackbar: Snackbar?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_news, container, false)
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        if (snackbar != null) {
            snackbar?.dismiss()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
     //  binding.newsRv.setHasFixedSize(true)

        adapter = NewsPagingAdapter(context!!)
        binding.newsRv.layoutManager = LinearLayoutManager(context!!)
        if ( binding.newsRv.adapter == null)
            binding.newsRv.adapter = adapter


        viewModel.dataComing.observe(this, Observer {
            adapter.submitList(it)

            adapter.onItemClick = {
                startActivity(Intent(context, NewsDetailsActivity::class.java).apply {
                    putExtra("obj", it)
                })
            }

        })


        handleObserverState()
        viewModel.onGetMyListingRequested("")

    }

    private fun handleObserverState(){
        viewModel.initialResult.observe(this, Observer {


            it.apply {
                when (this) {
                    is Resource.Progress -> {
                        if (loading)
                            dialog = context?.showLoadingDialog()!!
                        else
                            dialog?.dismiss()
                    }
                    is Resource.Success -> {
                        dialog?.dismiss()
                      ///  none.visibility = View.GONE
                    }
                    is Resource.Failure -> {
                        if (e is IOException) {
                            //context?.toast("Need Internet to Work ")
                            dialog?.dismiss()
                            snackbar = context?.showConnectionErrorView({

                                viewModel.onGetMyListingRequested("") })



                        } else {

                            if(e.message.isNullOrEmpty())
                                context?.toast("Something is wrong !!")
                            else{
                                if(e.message == "no data"){
                                    dialog?.dismiss()
                                  ///  none.visibility = View.VISIBLE
                                    }
                                else context?.toast(e.message.toString())
                            }}
                    }


                } } })



        // after load more callback ...
        viewModel.networkStatus.observe(this, Observer {
            adapter.networkStatus = it
            when (it) { // preview snack bar with retry option ....
                NetworkStatus.ERROR -> context?.showConnectionErrorView({
                    viewModel.onRetryGettingPagedShows()

                })
                else -> Unit
            }
        })


    }





}

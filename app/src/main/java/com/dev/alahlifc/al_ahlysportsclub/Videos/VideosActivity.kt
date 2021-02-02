package com.dev.alahlifc.al_ahlysportsclub.Videos

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Details.DetailsActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityVideosBinding
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

class VideosActivity : LocalizationActivity() {

    private lateinit var binding: ActivityVideosBinding
    private lateinit var viewModel: VideosViewModel
    private lateinit var context : Context
    private  var dialog: Dialog? = null
    private lateinit var adapter : VideosPagingAdapter
    private  var snackbar: Snackbar?= null





    override fun onStop() {
        super.onStop()
        if (snackbar != null) {
            snackbar?.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_videos)
        overridePendingTransition(R.anim.youtube_enter_animation, 0)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_videos)
        context = this
        viewModel = ViewModelProviders.of(this).get(VideosViewModel::class.java)


        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()


        viewModel = ViewModelProviders.of(this).get(VideosViewModel::class.java)
        //  binding.newsRv.setHasFixedSize(true)

        adapter = VideosPagingAdapter(context!!)
        binding.videosRv.layoutManager = LinearLayoutManager(context!!)
        if ( binding.videosRv.adapter == null)
            binding.videosRv.adapter = adapter


        viewModel.dataComing.observe(this, Observer { data ->
            adapter.submitList(data)
            adapter.onItemClick={

                startActivity(Intent(context,DetailsActivity::class.java).apply {
                    putExtra("upload_id",data.get(it)!!.uploadId)
                    putExtra("title",data.get(it)!!.name)
                    putExtra("desc",data.get(it)!!.content)
                    putExtra("link",data.get(it)!!.link)
                    putExtra("video",data.get(it)!!.video)
                })

            }

        })


        handleObserverState()
        viewModel.onGetMyListingRequested("")




    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@VideosActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
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

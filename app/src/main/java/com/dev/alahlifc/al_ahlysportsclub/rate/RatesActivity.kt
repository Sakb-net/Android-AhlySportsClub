package com.dev.alahlifc.al_ahlysportsclub.rate

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityRatesBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import androidx.recyclerview.widget.DefaultItemAnimator
import com.dev.alahlifc.al_ahlysportsclub.AddRate.AddRateActivity
import com.dev.alahlifc.al_ahlysportsclub.models.mAddComment
import com.dev.alahlifc.al_ahlysportsclub.models.mComments
import com.dev.alahlifc.al_ahlysportsclub.utils.EndlessRecyclerViewScrollingListener
import timber.log.Timber


class RatesActivity : LocalizationActivity() {

    private lateinit var binding: ActivityRatesBinding
    private lateinit var viewModel: RatesViewModel
    private lateinit var adapter: RatesAdapter
    private lateinit var context : Context
    private  var dialog: Dialog? = null
    private var link : String = ""



    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 700) {
            if (resultCode == Activity.RESULT_OK) {

               val newComment = data!!.getParcelableExtra<mAddComment>("obj")!!

                var owner = ""
                val u = PrefManager.getUser()
                if(u!=null)
                    owner = "1"

                adapter.addComment(
                    mComments.Data(null,newComment.data.content
                        ,mComments.CreatedAt(newComment.data.createdAt),newComment.data.createdAt,false,
                        newComment.data.link,"0",owner,newComment.data.parentOneId,
                        newComment.data.userImage,""+newComment.data.name,newComment.data.rate.toInt()))


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rates)
        context = this
        link = intent.getStringExtra("link")?:""

        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()
        viewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)
        binding.ratesRv.setHasFixedSize(true)




        // /binding.ratesRv.isFocusable = false
        binding.ratesRv.setItemAnimator(DefaultItemAnimator())

        adapter = RatesAdapter(mutableListOf(), link)
        adapter.setHasStableIds(true)
        binding.ratesRv.adapter = adapter
     ///   setupEndlessScroll(binding.ratesRv)


        getObservableData(link)

     /**   binding.addBtn.setOnClickListener {
            startActivityForResult(Intent(context, AddCommentActivity::class.java)
                .putExtra("type","news")
                .putExtra("link",news.link),
                700)
        }

        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, ""+getString(R.string.app_name))
            intent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(Intent.createChooser(intent, "choose one"))
        }*/

        binding.positiveBtn.setOnClickListener {

            startActivityForResult(Intent(context, AddRateActivity::class.java)
                .putExtra("type","product")
                .putExtra("link",link),
                700)
       /**   startActivity(
                Intent(this, AddRateActivity::class.java)
                    .putExtra("link", link)
                    .putExtra("video_link","")
                    .putExtra("reply",false)
                    .putExtra("type","product")
            )*/
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getObservableData(link: String) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        var token = ""
        if (user!=null)
            token = ""+user?.access_token


        viewModel.Comments(token,link,"product",viewModel.page,"10")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                             //   binding.progres.visibility = View.VISIBLE
                            //  binding.shimmerViewContainer.startShimmerAnimation()
                             dialog = context?.showLoadingDialog()!!
                            else;
                              //  binding.progres.visibility = View.GONE
                            //   binding.shimmerViewContainer.stopShimmerAnimation()
                            //  binding.shimmerViewContainer.visibility = View.GONE
                           // dialog?.dismiss()
                        }
                        is Resource.Success -> {
                            //  binding.shimmerViewContainer.stopShimmerAnimation()
                            //  binding.shimmerViewContainer.visibility = View.GONE
                           // binding.progres.visibility = View.GONE
                           /// dialog?.dismiss()

                         //   adapter = RatesAdapter(data.data, link)
                           // if (binding.ratesRv.adapter == null)
                             //   binding.ratesRv.adapter = adapter




                           // if (viewModel.page=="0")

                          /**  if (data.statusCode==11){

                                PrefManager.saveUser(null)
                                Timber.e("failed#########")
                                getObservableDataFailed(link)



                            }else {*/

                                dialog?.dismiss()

                                if (!data.data.isNullOrEmpty())
                                    adapter.setItems(data.data)
                                adapter.onDeleteClick = { position ->
                                    getObservableDeleteComment(position, "" + data.data[position]?.link)
                                }
                                /** else
                                adapter.loadMore(data.data)

                                if(data.data.size>0){
                                viewModel.page =""+( viewModel.page.toInt()+1)
                                }*/

                                /*  binding.ratesRv.handlePagination({},{}, LinearLayoutManager(context),{
                                  toast("load more")
                              })*/

                                // todo only one ....
                                /**  val   endlessRecyclerViewScrollListener =
                                object : EndlessRecyclerViewScrollListener(LinearLayoutManager(context)) {
                                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

                                toast("load more ... ")

                                }
                                }*/

                                //  binding.ratesRv.addOnScrollListener(endlessRecyclerViewScrollListener)


                                /////  binding.commentsRv.visibility = View.VISIBLE
                                /***
                                adapter.onItemClick = { position ->


                                if (user!=null)
                                getObservableAddLikeUnLike(position,""+data.data[position]?.link)
                                else

                                {
                                context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                                //   it ->it?.dismiss()
                                val intent = Intent(this@NewsDetailsActivity , LoginActivity::class.java)
                                startActivity(intent)

                                },{ it -> it?.dismiss()
                                })


                                }
                                }
                                adapter.onOpenReplyClick = {

                                startActivity(
                                Intent(this@NewsDetailsActivity, RepliesActivity::class.java)
                                .putExtra("obj",data.data[it])
                                .putExtra("video_link",link)
                                )
                                }
                                adapter.onDeleteClick = {position ->

                                getObservableDeleteComment(position,""+data.data[position]?.link)

                                }*/
                          //  }

                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context.toast(getString(R.string.need_internet))
                            } else {
                                context.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }

  /*  private fun getObservableDataFailed(link: String) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        var token = ""
        if (user!=null)
            token = ""+user?.access_token


        viewModel.reloadComments(token,link,"product",viewModel.page,"10")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading);
                            //   binding.progres.visibility = View.VISIBLE
                            //  binding.shimmerViewContainer.startShimmerAnimation()
                                //dialog = context?.showLoadingDialog()!!
                         //   else;
                            //  binding.progres.visibility = View.GONE
                            //   binding.shimmerViewContainer.stopShimmerAnimation()
                            //  binding.shimmerViewContainer.visibility = View.GONE
                            // dialog?.dismiss()
                        }
                        is Resource.Success -> {
                            //  binding.shimmerViewContainer.stopShimmerAnimation()
                            //  binding.shimmerViewContainer.visibility = View.GONE
                            // binding.progres.visibility = View.GONE
                            /// dialog?.dismiss()

                            //   adapter = RatesAdapter(data.data, link)
                            // if (binding.ratesRv.adapter == null)
                            //   binding.ratesRv.adapter = adapter




                            // if (viewModel.page=="0")


                                dialog?.dismiss()

                                if (!data.data.isNullOrEmpty())
                                    adapter.setItems(data.data)
                                adapter.onDeleteClick = { position ->
                                    getObservableDeleteComment(position, "" + data.data[position]?.link)
                                }
                                *//** else
                                adapter.loadMore(data.data)

                                if(data.data.size>0){
                                viewModel.page =""+( viewModel.page.toInt()+1)
                                }*//*

                                *//*  binding.ratesRv.handlePagination({},{}, LinearLayoutManager(context),{
                                  toast("load more")
                              })*//*

                                // todo only one ....
                                *//**  val   endlessRecyclerViewScrollListener =
                                object : EndlessRecyclerViewScrollListener(LinearLayoutManager(context)) {
                                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

                                toast("load more ... ")

                                }
                                }*//*

                                //  binding.ratesRv.addOnScrollListener(endlessRecyclerViewScrollListener)


                                /////  binding.commentsRv.visibility = View.VISIBLE
                                *//***
                                adapter.onItemClick = { position ->


                                if (user!=null)
                                getObservableAddLikeUnLike(position,""+data.data[position]?.link)
                                else

                                {
                                context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                                //   it ->it?.dismiss()
                                val intent = Intent(this@NewsDetailsActivity , LoginActivity::class.java)
                                startActivity(intent)

                                },{ it -> it?.dismiss()
                                })


                                }
                                }
                                adapter.onOpenReplyClick = {

                                startActivity(
                                Intent(this@NewsDetailsActivity, RepliesActivity::class.java)
                                .putExtra("obj",data.data[it])
                                .putExtra("video_link",link)
                                )
                                }
                                adapter.onDeleteClick = {position ->

                                getObservableDeleteComment(position,""+data.data[position]?.link)

                                }*//*


                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context.toast(getString(R.string.need_internet))
                            } else {
                                context.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }*/

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@RatesActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)

    }

    private fun setupEndlessScroll(recyclerView : RecyclerView) {
        val endlessScrollListener: EndlessRecyclerViewScrollingListener = object : EndlessRecyclerViewScrollingListener(recyclerView.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
               // viewModel.onLoadMore()
                toast("load more")
                getObservableData(link)
            }

        }
        recyclerView.addOnScrollListener(endlessScrollListener)
    }

    private fun getObservableDeleteComment(pos : Int,comment_link : String ) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        viewModel.DeleteComment(""+user?.access_token,comment_link,"product")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                dialog = context?.showLoadingDialog()!!
                            else
                                dialog?.dismiss()
                        }
                        is Resource.Success -> {
                            adapter.DeleteCmnt(pos)


                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context.toast(getString(R.string.need_internet))
                            } else {
                                context.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }

}

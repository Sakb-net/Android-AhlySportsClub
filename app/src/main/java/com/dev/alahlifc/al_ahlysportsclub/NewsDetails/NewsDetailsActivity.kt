package com.dev.alahlifc.al_ahlysportsclub.NewsDetails

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.AddComment.AddCommentActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Details.DetailsCommentsAdapter
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityNewsDetailsBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mAddComment
import com.dev.alahlifc.al_ahlysportsclub.models.mComments
import com.dev.alahlifc.al_ahlysportsclub.models.mNews
import com.dev.alahlifc.al_ahlysportsclub.replies.RepliesActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException

class NewsDetailsActivity : LocalizationActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding
    private lateinit var viewModel: NewsDetailsViewModel
    private lateinit var adapter: NewsDetailsCommentsAdapter
    private lateinit var context : Context
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_details)
        context = this
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()
        val news : mNews.Data = intent.getParcelableExtra("obj")
         binding.newsData = news

        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel::class.java)
        getObservableData(news.link!!)

        binding.addBtn.setOnClickListener {
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
        }
    }


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
                    newComment.data.userImage,""+newComment.data.name,0))

                /* val cmnt = data!!.getStringExtra("result")
                 val name = data!!.getStringExtra("name")

                Timber.e("cmnt "+cmnt)
                 var obj = mComments.Data()
                 obj.content = cmnt
                 val user = PrefManager.getUser()

                 if (name!=null && name.trim().length>0)
                     obj.parentUserName = name
                 else {
                     obj.parentUserName = "" + user?.display_name
                     obj.parentUserImage = ""+Constants.baseUrl + user?.image
                 }

                 adapter.addComment(obj)*/
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@NewsDetailsActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)

    }



    private fun getObservableData(link: String) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        var token = ""
        if (user!=null)
            token = ""+user?.access_token


        viewModel.Comments(token,link,"news","0","10")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                binding.progres.visibility = View.VISIBLE
                            //  binding.shimmerViewContainer.startShimmerAnimation()
                            // dialog = context?.showLoadingDialog()!!
                          //  else
                               // binding.progres.visibility = View.GONE
                            //   binding.shimmerViewContainer.stopShimmerAnimation()
                            //  binding.shimmerViewContainer.visibility = View.GONE
                            //  dialog.dismiss()
                        }
                        is Resource.Success -> {
                            //  binding.shimmerViewContainer.stopShimmerAnimation()
                            //  binding.shimmerViewContainer.visibility = View.GONE



                            adapter = NewsDetailsCommentsAdapter(data.data, link)
                            if (binding.commentsRv.adapter == null)
                                binding.commentsRv.adapter = adapter

                            binding.progres.visibility = View.GONE

                            /////  binding.commentsRv.visibility = View.VISIBLE

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

                            }


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

    private fun getObservableAddLikeUnLike(pos : Int,comment_link : String ) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        viewModel.AddLikeUnLike(""+user?.access_token,comment_link,"comment_news")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading);
                            // dialog = context?.showLoadingDialog()!!
                            //  else
                            //   dialog.dismiss()
                        }
                        is Resource.Success -> {
                            adapter.updateLike(data.data,pos)


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


    private fun getObservableDeleteComment(pos : Int,comment_link : String ) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        viewModel.DeleteComment(""+user?.access_token,comment_link,"news")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}

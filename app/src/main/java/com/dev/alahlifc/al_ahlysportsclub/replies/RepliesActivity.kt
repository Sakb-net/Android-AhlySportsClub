package com.dev.alahlifc.al_ahlysportsclub.replies

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityRepliesBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mComments
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException

class RepliesActivity : LocalizationActivity() {

    private lateinit var binding: ActivityRepliesBinding
    private lateinit var dialog: Dialog
    private lateinit var adapter: repliesAdapter
    private lateinit var viewModel: repliesViewModel
    private var video_link :String ?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_replies)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_replies)

        viewModel = ViewModelProviders.of(this).get(repliesViewModel::class.java)


        binding.toolbar.setTitle("")

        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()

        val repies = intent.getParcelableExtra<mComments.Data>("obj")
        video_link = intent.getStringExtra("video_link")

        adapter = repliesAdapter(repies.childComments!!,""+video_link)
        if (binding.commentsRv.adapter == null)
            binding.commentsRv.adapter = adapter
        adapter.onItemClick = { position ->


            val u = PrefManager.getUser()
            if (u!=null)
            getObservableAddLikeUnLike(position,""+repies.childComments!![position]!!.link!!)
            else
            {
                this@RepliesActivity?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                    //   it ->it?.dismiss()
                    val intent = Intent(this@RepliesActivity , LoginActivity::class.java)
                    startActivity(intent)

                },{ it -> it?.dismiss()
                })


            }
        }
        adapter.onDeleteClick = {position ->
            getObservableDeleteComment(position,""+ repies.childComments?.get(position)?.link)
        }

    }

    private fun getObservableAddLikeUnLike(pos : Int,comment_link : String ) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        viewModel.AddLikeUnLike(""+user?.access_token,comment_link,"comment_video")
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
                                this@RepliesActivity?.toast(getString(R.string.need_internet))
                            } else {
                                this@RepliesActivity?.toast(getString(R.string.something_wrong))
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

    private fun getObservableDeleteComment(pos : Int,comment_link : String ) {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        viewModel.DeleteComment(""+user?.access_token,comment_link,"comment_video")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                dialog = showLoadingDialog()!!
                            else
                                dialog.dismiss()
                        }
                        is Resource.Success -> {
                            adapter.DeleteCmnt(pos)


                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                toast(getString(R.string.need_internet))
                            } else {
                                toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@RepliesActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)



    }
}

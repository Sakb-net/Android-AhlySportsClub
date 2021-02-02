package com.dev.alahlifc.al_ahlysportsclub.Details

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.AddComment.AddCommentActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityDetailsBinding
import com.dev.alahlifc.al_ahlysportsclub.replies.RepliesActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import java.io.IOException
import android.app.Activity
import androidx.navigation.fragment.findNavController
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import cn.jzvd.JzvdStd
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.models.mAddComment
import com.dev.alahlifc.al_ahlysportsclub.models.mComments
import timber.log.Timber


class DetailsActivity : LocalizationActivity()/* , YouTubePlayer.OnInitializedListener */ {

    private lateinit var viewModel: DetailsCommentsViewModel
    private lateinit var adapter: DetailsCommentsAdapter
    private lateinit var dialog: Dialog

    private var onInitializedListener: YouTubePlayer.OnInitializedListener? = null


    private var mYoutubePlayer: YouTubePlayer? = null
    private var frag : YouTubePlayerSupportFragment ? = null
    private lateinit var binding: ActivityDetailsBinding
   // private lateinit var viewModel: CategoriesViewModel
    private lateinit var context : Context
    private var upload_id : String = ""
    private var titleTv : String = ""
    private var desc : String = ""
    private var link : String = ""
    private var video : String = ""
  //  private lateinit var adapter: CategoriesAdapter
   // private lateinit var dialog: Dialog
   // private lateinit var textCartItemCount : TextView


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 700) {
            if (resultCode == Activity.RESULT_OK) {
                val newComment = data!!.getParcelableExtra<mAddComment>("obj")!!

                var owner = ""
                val u = PrefManager.getUser()
                if(u!=null)
                    owner = "1"

                adapter.addComment(mComments.Data(null,newComment.data.content
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

  override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.youtube_exit_animation)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }
    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_details)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        context = this
        viewModel = ViewModelProviders.of(this).get(DetailsCommentsViewModel::class.java)
      ///  shimmer_view_container.setBaseAlpha(0.4f)

        upload_id = intent.getStringExtra("upload_id")
        Timber.e("========= "+upload_id)
        titleTv = intent.getStringExtra("title")
        desc = intent.getStringExtra("desc")
        link = intent.getStringExtra("link")
        video = intent.getStringExtra("video")
         frag = supportFragmentManager.findFragmentById(R.id.player_fragment) as YouTubePlayerSupportFragment


        if(upload_id==""){

            binding.yVideoll.visibility = View.INVISIBLE
            binding.jzvdVideoll.visibility = View.VISIBLE

       binding.jzVideo.setUp(Constants.baseUrl+video, "", JzvdStd.SCREEN_NORMAL)
        Glide.with(this)
            .load(Constants.baseUrl+video)
            .centerCrop()
            .into( binding.jzVideo.thumbImageView)


        }else {


            binding.jzvdVideoll.visibility = View.INVISIBLE
            binding.yVideoll.visibility = View.VISIBLE




            onInitializedListener = object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider?,
                    youTubePlayer: YouTubePlayer?,
                    wasRestored: Boolean
                ) {

                    youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
                    youTubePlayer?.setPlaybackEventListener(playbackEventListener)
                    if (!wasRestored) {
                        youTubePlayer?.cueVideo(upload_id)
                    }
                    mYoutubePlayer = youTubePlayer

                }

                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                    ///Timber.e("////////////////"+p0?.toString())
                   /// Timber.e("////////////////"+p1?.toString())


                   /// context?.toast(""+p1?.name)
                }
            }
            /////////////
            frag!!.initialize(getString(R.string.GOOGLE_API_KEY), onInitializedListener)

        }







        title = "Video"
        binding.toolbar.setTitle("")
        binding.titleTv.text = titleTv
        binding.descriptionTv.text = desc

        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()


      //  youtube_player.initialize(getString(R.string.GOOGLE_API_KEY), this)
        binding.addBtn.setOnClickListener {
            startActivityForResult(Intent(context,AddCommentActivity::class.java)
                .putExtra("link",link)
                .putExtra("type","video")
                ,
                700)
        }


        getObservableData()

        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, ""+getString(R.string.app_name))
            intent.putExtra(Intent.EXTRA_TEXT, ""+binding.titleTv.text)
            startActivity(Intent.createChooser(intent, "choose one"))
        }
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@DetailsActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle, binding.titleTv, binding.descriptionTv, binding.textView8)
    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onPlaying() {

        }

        override fun onPaused() {

        }

        override fun onStopped() {

        }

        override fun onBuffering(b: Boolean) {

        }

        override fun onSeekTo(i: Int) {

        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onLoading() {

        }

        override fun onLoaded(s: String) {

        }

        override fun onAdStarted() {

        }

        override fun onVideoStarted() {

        }

        override fun onVideoEnded() {

        }

        override fun onError(errorReason: YouTubePlayer.ErrorReason) {

        }
    }


    private fun getObservableData() {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()

        var token = ""
        if (user!=null)
            token = ""+user?.access_token


        viewModel.Comments(token,link,"video","0","10")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                binding.progres.visibility = View.VISIBLE
                              //  binding.shimmerViewContainer.startShimmerAnimation()
                               // dialog = context?.showLoadingDialog()!!
                            else
                                binding.progres.visibility = View.GONE
                             //   binding.shimmerViewContainer.stopShimmerAnimation()
                          //  binding.shimmerViewContainer.visibility = View.GONE
                              //  dialog.dismiss()
                        }
                        is Resource.Success -> {
                          //  binding.shimmerViewContainer.stopShimmerAnimation()
                          //  binding.shimmerViewContainer.visibility = View.GONE
                            binding.progres.visibility = View.GONE

                            adapter = DetailsCommentsAdapter(data.data, link)
                            if (binding.commentsRv.adapter == null)
                                binding.commentsRv.adapter = adapter

                          /////  binding.commentsRv.visibility = View.VISIBLE

                            adapter.onItemClick = { position ->


                                if (user!=null)
                                    getObservableAddLikeUnLike(position,""+data.data[position]?.link)
                                else

                                {
                                    context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                                        //   it ->it?.dismiss()
                                        val intent = Intent(this@DetailsActivity , LoginActivity::class.java)
                                        startActivity(intent)

                                    },{ it -> it?.dismiss()
                                    })


                                }
                            }
                            adapter.onOpenReplyClick = {

                                startActivity(Intent(this@DetailsActivity, RepliesActivity::class.java)
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

        viewModel.DeleteComment(""+user?.access_token,comment_link,"video")
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


    override fun onDestroy() {
        super.onDestroy()
        onInitializedListener = null
    }
}

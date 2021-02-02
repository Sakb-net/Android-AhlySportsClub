package com.dev.alahlifc.al_ahlysportsclub.MatchDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cn.jzvd.JzvdStd
import com.bumptech.glide.Glide
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentNewsFeedBinding


import timber.log.Timber
import java.util.regex.Pattern
import androidx.fragment.app.FragmentTransaction
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment


class NewsFeedFragment : Fragment() {

    private lateinit var binding : FragmentNewsFeedBinding
    private var onInitializedListener: YouTubePlayer.OnInitializedListener? = null


    private var mYoutubePlayer: YouTubePlayer? = null
    private var frag : YouTubePlayerSupportFragment? = null
    private var upload_id : String = ""
    private var titleTv : String = ""
    private var desc : String = ""
    private var link : String = ""
    private var video : String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_news_feed, container, false)
        frag = YouTubePlayerSupportFragment.newInstance() as YouTubePlayerSupportFragment
        // レイアウトにYouTubeフラグメントを追加
        fragmentManager!!.beginTransaction().replace(R.id.playerFragment, frag as Fragment)
            .commit()
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val obj : mMatches.Data?  = arguments!!.getParcelable("obj")
        Timber.e("===="+obj)
       try {
           var temp = extractYTId("" + obj?.video)!!
           upload_id = temp
       }catch (e:Exception){
           upload_id=""
       }


        video = obj?.video!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // frag = fragmentManager!!.findFragmentById(R.id.player_fragment) as YouTubePlayerSupportFragment
        // YouTubeフラグメントインスタンスを取得



        if(upload_id.isNullOrEmpty()||upload_id.trim().length==0){

            binding.yVideoll.visibility = View.INVISIBLE
            binding.jzvdVideoll.visibility = View.VISIBLE

            binding.jzVideo.setUp(Constants.baseUrl+video, "", JzvdStd.SCREEN_NORMAL)
            Glide.with(this)
                .load(Constants.baseUrl+video)
                .centerCrop()
                .into( binding.jzVideo.thumbImageView)


        }
        else {


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

    override fun onDestroy() {
        super.onDestroy()
        onInitializedListener = null
    }

    fun extractYTId(ytUrl: String): String? {
        var vId: String? = null
        val pattern = Pattern.compile(
            "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
            Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(ytUrl)
        if (matcher.matches()) {
            vId = matcher.group(1)
        }
        return vId
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}

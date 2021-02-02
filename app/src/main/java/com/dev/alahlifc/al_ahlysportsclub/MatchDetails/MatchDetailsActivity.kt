package com.dev.alahlifc.al_ahlysportsclub.MatchDetails

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import cn.jzvd.Jzvd
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityMatchDetailsBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMatches
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import kotlin.collections.ArrayList

class MatchDetailsActivity : LocalizationActivity() {

    private lateinit var binding: ActivityMatchDetailsBinding
    private var pastmatch : mMatches.Data? = null


    //
  ///  private lateinit var menuNewsFeed: MenuItem
  ///  private lateinit var menuArchive: MenuItem

    private var params: CollapsingToolbarLayout.LayoutParams? = null
    private var originalBottom = 0
    private var collapsedTop = 0
    private var pinDistance = 0

    private var isBadgeVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_match_details)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_details)

       pastmatch = intent.getParcelableExtra("obj")

        initToolbar()

        initView()


        binding.headerlayout.toolbar.setTitle("")
        setSupportActionBar(binding.headerlayout.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
       // changeViewsFonts()

        //Timber.e("---> "+player.toString())
        binding.matchData = pastmatch

    }

    override fun onResume() {
        super.onResume()
        binding.headerlayout.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (params == null)
                    return

                val oldBottom = params?.bottomMargin
                var newBottom = originalBottom * (collapsedTop - verticalOffset) / pinDistance

                if (newBottom > originalBottom)
                    newBottom = originalBottom

                if (newBottom < 0)
                    newBottom = 0

                if (newBottom != oldBottom) {
                    params?.bottomMargin = newBottom
                    binding.headerlayout.tabs.layoutParams = params
                }
            }

        })
    }

    override fun onPause() {
        binding.headerlayout.appBar.removeOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
           override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
               if (params == null)
                   return

               val oldBottom = params?.bottomMargin
               var newBottom = originalBottom * (collapsedTop - verticalOffset) / pinDistance

               if (newBottom > originalBottom)
                   newBottom = originalBottom

               if (newBottom < 0)
                   newBottom = 0

               if (newBottom != oldBottom) {
                   params?.bottomMargin = newBottom
                   binding.headerlayout.tabs.layoutParams = params
               }
           }
       })
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) return
        if (params != null) return

        params =  binding.headerlayout.tabs.layoutParams as? CollapsingToolbarLayout.LayoutParams
        originalBottom = params?.bottomMargin ?: 0
        collapsedTop = - binding.headerlayout.appBar.totalScrollRange
        pinDistance = - binding.headerlayout.tabs.top + 100
    }




    private fun initToolbar() {
        setSupportActionBar( binding.headerlayout.toolbar)
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }



    private fun initView() {
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        binding.  viewPager.adapter = pagerAdapter
        binding.headerlayout. tabs.setupWithViewPager(  binding.viewPager)
        binding.headerlayout. tabs.addOnTabSelectedListener(object : TabLayout.ViewPagerOnTabSelectedListener(  binding. viewPager) {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                super.onTabReselected(tab)

                if (  binding.viewPager.currentItem == 0) {
                    // TODO scroll news feed up
                } else {
                    // TODO scroll archive up
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                super.onTabSelected(tab)

                //if (  binding.viewPager.currentItem == 0) {
                   // menuNewsFeed.isChecked = true
                  ///  binding.   btnFab.show()
                //} else {
                   // menuArchive.isChecked = true
                  ///  binding.  btnFab.hide()
               // }
            }
        })

    //   binding. btnBadge.alpha = 0f
    //    binding.   btnBadge.translationY = -100f
    ///    binding.   btnBadge.visibility = View.GONE
      //  binding.  btnBadge.setOnClickListener(onBadgeClicked)

       // binding.  btnFab.setOnClickListener(this::onFabClicked)
    }

//    private fun showBadge(content: String) {
//        if (!isBadgeVisible) {
//          binding. btnBadge.animate()
//                .alpha(1f)
//                .translationY(0f)
//                .setDuration(300)
//                .setInterpolator(DecelerateInterpolator())
//                .setStartDelay(0)
//                .setListener(object : Animator.AnimatorListener {
//                    override fun onAnimationRepeat(animation: Animator?) {
//
//                    }
//
//                    override fun onAnimationCancel(animation: Animator?) {
//                    }
//
//                    override fun onAnimationStart(animation: Animator) {
//                      binding. btnBadge.visibility = View.VISIBLE
//                     binding. btnBadge.text = content
//                    }
//
//                    override fun onAnimationEnd(animation: Animator) {
//                        isBadgeVisible = true
//                    }
//                })
//                .start()
//        } else {
//         binding.  btnBadge.animate()
//                .scaleX(1.1f)
//                .scaleY(1.1f)
//                .setDuration(150)
//                .setInterpolator(AccelerateInterpolator())
//                .setStartDelay(0)
//                .setListener(object : Animator.AnimatorListener {
//                    override fun onAnimationRepeat(animation: Animator?) {
//
//                    }
//
//                    override fun onAnimationCancel(animation: Animator?) {
//                    }
//
//                    override fun onAnimationStart(animation: Animator?) {
//                    }
//
//                    override fun onAnimationEnd(animation: Animator) {
//                       binding. btnBadge.text = content
//                       binding. btnBadge.animate()
//                            .scaleX(1f)
//                            .scaleY(1f)
//                            .setDuration(150)
//                            .setInterpolator(DecelerateInterpolator())
//                            .setStartDelay(0)
//                            .setListener(null)
//                            .start()
//                    }
//                })
//                .start()
//        }
//    }
//
//    private fun hideBadge() {
//        if (isBadgeVisible) {
//            isBadgeVisible = false
//           binding.btnBadge.animate()
//                .alpha(0f)
//                .translationY(-100f)
//                .setDuration(300)
//                .setInterpolator(DecelerateInterpolator())
//                .setStartDelay(100)
//                .setListener(object : Animator.AnimatorListener {
//                    override fun onAnimationRepeat(animation: Animator?) {
//
//                    }
//
//                    override fun onAnimationCancel(animation: Animator?) {
//                    }
//
//                    override fun onAnimationStart(animation: Animator?) {
//                    }
//
//                    override fun onAnimationEnd(animation: Animator) {
//                        binding.btnBadge.visibility = View.GONE
//                    }
//                })
//                .start()
//        }
//    }


//    private fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
//
//    }



  /*  private fun onFabClicked(view: View) {
        val random = Random().nextInt(9) + 1
        showBadge("${random} new posts")
    }*/

//    private fun onBadgeClicked(view: View) {
//       scrollView.scrollTo(0, 0)
//        hideBadge()
//    }


    private inner class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val fragments = ArrayList<Fragment>()

        init {
            val monitor = NewsFeedFragment()
            val archive = ArchiveFragment()
            monitor.arguments = Bundle().apply {
            putParcelable("obj", pastmatch)
        }

            archive.arguments = Bundle().apply {
                putParcelable("obj", pastmatch)
            }
            fragments.add(monitor)
            fragments.add(archive)
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) getString(R.string.match_video)
            else getString(R.string.statistics)
        }

        override fun getCount(): Int {
            return 2
        }
    }

 /**   private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@MatchDetailsActivity,
            Constants.FONT_REGULAR,binding.titleName, binding.team1Tv, binding.team2Tv
           )



    }*/


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


}


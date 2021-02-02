package com.dev.alahlifc.al_ahlysportsclub.Homesnap


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.alahlifc.al_ahlysportsclub.Album.AlbumActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Categories.CategoriesActivity
import com.dev.alahlifc.al_ahlysportsclub.Home.HomeViewModel
import com.dev.alahlifc.al_ahlysportsclub.NewestProducts.NewestProductsActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.Videos.VideosActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import kotlinx.android.synthetic.main.fragment_home_snap.*
import java.io.IOException
import com.dev.alahlifc.al_ahlysportsclub.utils.LinePagerIndicatorDecoration
import com.takusemba.multisnaprecyclerview.OnSnapListener






class HomeSnapFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home_snap, container, false)
            setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_store -> {

               /* val user = PrefManager.getUser()
                if (user==null){
                    context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                    //   it ->it?.dismiss()
                    val intent = Intent(activity , LoginActivity::class.java)
                    startActivity(intent)


                },{
                        it -> it?.dismiss()
                    findNavController().navigateUp()

                })}
                else{*/
                    context?.startActivity(Intent(context, CategoriesActivity::class.java))
                    activity?.overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
               // }

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        ///shimmer_view_container.setBaseAlpha(0.8f)
               shimmer_view_container2.setBaseAlpha(0.4f)



        val sliderManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            slider_recycler_view.layoutManager = sliderManager
            // val firstRecyclerView = findViewById(R.id.first_recycler_view) as MultiSnapRecyclerView
            val firstManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            first_recycler_view.layoutManager = firstManager
            // val secondRecyclerView = findViewById(R.id.second_recycler_view) as MultiSnapRecyclerView
            val secondManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            second_recycler_view.layoutManager = secondManager
            // val thirdRecyclerView = findViewById(R.id.third_recycler_view) as MultiSnapRecyclerView
            val thirdManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            third_recycler_view.layoutManager = thirdManager
            // val thirdRecyclerView = findViewById(R.id.third_recycler_view) as MultiSnapRecyclerView
            val fourthManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            fourth_recycler_view.layoutManager = fourthManager


            loadData()


    }

    private fun loadData() {
        val lang = PrefManager.getLanguage()
        viewModel.home(lang!!,"10")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading) {
                                ///shimmer_view_container.startShimmerAnimation()
                                shimmer_view_container2.startShimmerAnimation()
                            }
                            //    dialog = context?.showLoadingDialog()!!
                            else{
                                ///shimmer_view_container.stopShimmerAnimation()
                                shimmer_view_container2.stopShimmerAnimation()
                                shimmer_view_container2.visibility = View.GONE
                            }
                               // dialog?.dismiss()
                        }
                        is Resource.Success -> {

                            ///shimmer_view_container.stopShimmerAnimation()
                            shimmer_view_container2.stopShimmerAnimation()
                            shimmer_view_container2.visibility = View.GONE


                          /*  val ads = data.data
                            vp_on_boarding_container.adapter = SplashAdapter(context!!,ads).apply {
                                onItemClick={
                                    context?.startActivity(Intent(context, CategoriesActivity::class.java))
                                    activity?.overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
                                }
                            }
                            dotsLayout.removeAllViews()
                            val dotsImages = arrayOfNulls<View>(2)
                            val params = LinearLayout.LayoutParams(50, 20)
                            params.setMargins(5, 0, 5, 0)
                            for (i in 0 until 2) {
                                dotsImages[i] = View(dotsLayout.context)
                                dotsImages[i]?.layoutParams = params
                                dotsImages[i]?.setBackgroundColor(Color.parseColor("#bebbbf"))
                                dotsLayout.addView(dotsImages[i])

                            }
                            //Log.e("Selected_Page", position.toString())
                            for (i in 0 until 2) {
                                dotsImages[i]?.setBackgroundColor(Color.parseColor("#bebbbf"))
                            }
                            dotsImages[0]?.setBackgroundColor(Color.parseColor("#008640"))
                            vp_on_boarding_container.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
                                override fun onPageScrollStateChanged(state: Int) {

                                }

                                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                                }

                                override fun onPageSelected(position: Int) {

                                    viewModel.lastSliderPosition = position
                                    /// context?.toast("pos"+position)
                                    dotsLayout.removeAllViews()
                                    val dotsImages = arrayOfNulls<View>(2)
                                    val params = LinearLayout.LayoutParams(50, 20)
                                    params.setMargins(5, 0, 5, 0)
                                    for (i in 0 until 2) {
                                        dotsImages[i] = View(dotsLayout.context)
                                        dotsImages[i]?.layoutParams = params
                                        dotsImages[i]?.setBackgroundColor(Color.parseColor("#bebbbf"))
                                        dotsLayout.addView(dotsImages[i])

                                    }

                                    //Log.e("Selected_Page", position.toString())


                                    for (i in 0 until 2) {
                                        dotsImages[i]?.setBackgroundColor(Color.parseColor("#bebbbf"))
                                    }
                                    dotsImages[position]?.setBackgroundColor(Color.parseColor("#008640"))

                                }

                            })*/


                          //  slider_recycler_view
                            val ads = data.data
                            val zeroAdapter = SliderSnapAdapter(ads)
                            slider_recycler_view.adapter = zeroAdapter
                            dotsLayout.removeAllViews()
                            val dotsImages = arrayOfNulls<View>(2)
                            val params = LinearLayout.LayoutParams(50, 20)
                            params.setMargins(5, 0, 5, 0)
                            for (i in 0 until 2) {
                                dotsImages[i] = View(dotsLayout.context)
                                dotsImages[i]?.layoutParams = params
                                dotsImages[i]?.setBackgroundColor(Color.parseColor("#bebbbf"))
                                dotsLayout.addView(dotsImages[i])
                            }

                            //Log.e("Selected_Page", position.toString())


                            for (i in 0 until 2) {
                                dotsImages[i]?.setBackgroundColor(Color.parseColor("#bebbbf"))
                            }
                            dotsImages[ viewModel.lastSliderPosition]?.setBackgroundColor(Color.parseColor("#008640"))


                            slider_recycler_view.setOnSnapListener(object : OnSnapListener{
                                override fun snapped(position: Int) {
                                    viewModel.lastSliderPosition = position

                                    for (i in 0 until 2) {
                                        dotsImages[i]?.setBackgroundColor(Color.parseColor("#bebbbf"))
                                    }
                                    dotsImages[ viewModel.lastSliderPosition]?.setBackgroundColor(Color.parseColor("#008640"))

                                }
                            })

                          /*  slider_recycler_view.setOnSnapListener(OnSnapListener {
                                // do something with the position of the snapped view
                                viewModel.lastSliderPosition = it
                                /// context?.toast("pos"+position)




                            })*/

                            zeroAdapter.onItemClick = {
                                context?.startActivity(Intent(context, CategoriesActivity::class.java))
                                activity?.overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
                            }




                            val firstAdapter = NewsSnapAdapter(data.data?.news, data.data.news.size,0)
                                first_recycler_view.adapter = firstAdapter

                            val secondAdapter = AlbumsSnapAdapter(data.data?.albums, data.data?.albums.size,1)
                                second_recycler_view.adapter = secondAdapter

                            val thirdAdapter = VideosSnapAdapter(data.data?.videos,data.data?.videos.size, 2 )
                                third_recycler_view.adapter = thirdAdapter

                            val fourthAdapter = ProductsAdapter( data.data?.products ,  data.data?.products.size, 3)
                                fourth_recycler_view.adapter = fourthAdapter

                            title_tv.text = getString(R.string.latest_news)
                                more.text = getString(R.string.more)

                            title_tv2.text = getString(R.string.latest_albums)
                                more2.text = getString(R.string.more)

                            title_tv3.text = getString(R.string.latest_videos)
                                more3.text = getString(R.string.more)

                            title_tv4.text = getString(R.string.latest_products)
                                more4.text = getString(R.string.more)



                            more.setOnClickListener {
                                    findNavController().navigate(R.id.newsFragment)
                                }

                            more2.setOnClickListener {
                                    context?.startActivity(Intent(context, AlbumActivity::class.java))
                                }
                            more3.setOnClickListener {
                                    context?.startActivity(Intent(context, VideosActivity::class.java))
                                }
                            more4.setOnClickListener {
                                    context?.startActivity(Intent(context, NewestProductsActivity::class.java))
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

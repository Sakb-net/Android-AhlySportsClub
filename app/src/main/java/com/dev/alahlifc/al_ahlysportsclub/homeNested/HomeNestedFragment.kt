package com.dev.alahlifc.al_ahlysportsclub.homeNested


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dev.alahlifc.al_ahlysportsclub.Album.AlbumActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.BookTicket.BookTicketActivity
import com.dev.alahlifc.al_ahlysportsclub.Categories.CategoriesActivity
import com.dev.alahlifc.al_ahlysportsclub.Home.HomeViewModel
import com.dev.alahlifc.al_ahlysportsclub.NewestProducts.NewestProductsActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.Videos.VideosActivity
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import kotlinx.android.synthetic.main.fragment_home_nested.*
import timber.log.Timber
import java.io.IOException


class HomeNestedFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var dialog: Dialog ?= null
    private lateinit var    adapter : ParentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =
            inflater.inflate(R.layout.fragment_home_nested, container, false)
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
                context?.startActivity(Intent(context, CategoriesActivity::class.java))
                activity?.overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


//        vertical_recyclerview.apply {
//            setItemAnimator(DefaultItemAnimator())
//            setHasFixedSize(true)
//            setItemViewCacheSize(20)
//            setDrawingCacheEnabled(true)
//            setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)
//            getRecycledViewPool().setMaxRecycledViews(1,0);
//
//
//        }





        val lang = PrefManager.getLanguage()
        viewModel.home(lang!!,"10")
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
                            dialog?.dismiss()
                            val mutableList =
                                mutableListOf<Pair<String, mHome.Data?>?>()


                            mutableList.add(Pair<String,mHome.Data?>("ads",data.data!!))


                            val news = data.data?.news
                            if (!news.isNullOrEmpty())
                                mutableList.add(Pair<String,mHome.Data?>("news",data.data!!))

                            val albumm =  data.data?.albums
                            if (!albumm.isNullOrEmpty())
                                mutableList.add(Pair<String,mHome.Data?>("albumm",data.data!!))



                            val videos = data.data?.videos
                            if (!videos.isNullOrEmpty())
                                mutableList.add(Pair<String,mHome.Data?>("videos",data.data!!))


                            val products = data.data?.products
                            if (!products.isNullOrEmpty())
                                mutableList.add(Pair<String,mHome.Data?>("products",data.data!!))


                            adapter = ParentAdapter(mutableList)
                            if (vertical_recyclerview.adapter==null)
                                vertical_recyclerview.adapter =adapter


                            adapter?.onItemClick = {pos ->

                                if (pos==0){

                                    Timber.e("dddd"+data.data.matchNext?.link_ticket)

                                    context?.startActivity(Intent(context, BookTicketActivity::class.java)

                                        .putExtra("link",data.data.matchNext?.link_ticket?:""))
                                }
                                else if(pos==1) {
                                    findNavController().navigate(R.id.newsFragment)
                                }
                                else if(pos==2) {
                                    context?.startActivity(Intent(context, AlbumActivity::class.java))
                                }
                                else if(pos==3) {
                                    context?.startActivity(Intent(context, VideosActivity::class.java))
                                }
                                else
                                    context?.startActivity(Intent(context, NewestProductsActivity::class.java))
                            }

                           // verticalAdapter?.submitList(mutableList)

                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context?.toast(getString(R.string.need_internet))
                            } else {
                                Timber.e("dddddd "+e.stackTrace)
                                Timber.e("dddddd "+e.message)
                                context?.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })

    }




}

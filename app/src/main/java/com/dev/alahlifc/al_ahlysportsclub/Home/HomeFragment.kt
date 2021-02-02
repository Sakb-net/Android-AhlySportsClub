package com.dev.alahlifc.al_ahlysportsclub.Home
import android.app.Dialog
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.alahlifc.al_ahlysportsclub.Album.AlbumActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.BookTicket.BookTicketActivity
import com.dev.alahlifc.al_ahlysportsclub.Categories.CategoriesActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.Videos.VideosActivity
import com.dev.alahlifc.al_ahlysportsclub.models.mHome
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.dev.alahlifc.al_ahlysportsclub.utils.SparseIntArrayParcelable
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private  var verticalAdapter: VerticalAdapter? = null
    private lateinit var dialog: Dialog
    companion object {
        private const val KEY_HORIZONTAL_SCROLL_POSITIONS = "horizontal_scroll_positions"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v =
            inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        return v

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val horizontalScrollPositions: SparseIntArrayParcelable = savedInstanceState?.getParcelable(KEY_HORIZONTAL_SCROLL_POSITIONS) ?: SparseIntArrayParcelable()
        verticalAdapter = VerticalAdapter(horizontalScrollPositions)
        vertical_recyclerview?.adapter = verticalAdapter
        // Only necessary for use with CollapsingToolbarLayout
        vertical_recyclerview?.isNestedScrollingEnabled = false

        verticalAdapter?.onItemClick = {pos ->

            if (pos==0){
                context?.startActivity(Intent(context,BookTicketActivity::class.java))
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
                context?.toast("clicked!")
        }


        val lang = PrefManager.getLanguage()
        viewModel.home(lang!!,"10")
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
                            dialog.dismiss()
                            val mutableList =
                                mutableListOf<Pair<String,mHome.Data?>?>()


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




                            verticalAdapter?.submitList(mutableList)

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

        ////todo  verticalAdapter?.submitList(HomeRepository.verticalData)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_HORIZONTAL_SCROLL_POSITIONS, getHorizontalScrollPositions())
    }

    private fun getHorizontalScrollPositions(): SparseIntArrayParcelable? {
        val horizontalScrollPositions = verticalAdapter?.horizontalScrollPositions

        // horizontal scroll positions are tracked as the viewholders are detached from their window.
        // if the user changed the scroll position of visible items and those items weren't
        // ever detached, then their positions wouldn't be tracked in verticalAdapter.horizontalScrollPositions.
        // the logic below reconciles that absence.
        val layoutManager: LinearLayoutManager? = getVerticalLayoutManager()
        val firstVisible = layoutManager?.findFirstVisibleItemPosition()
        val lastVisible = layoutManager?.findLastVisibleItemPosition()

        if (firstVisible!=null&&lastVisible!=null)
            for (i in firstVisible..lastVisible) {

                if ( vertical_recyclerview?.findViewHolderForAdapterPosition(i) !=null) {

                    val verticalViewHolder =
                        vertical_recyclerview?.findViewHolderForAdapterPosition(i) as VerticalAdapter.VerticalViewHolder
                    horizontalScrollPositions?.put(i, verticalViewHolder.findFirstVisibleItemPosition())
                }
            }

        return horizontalScrollPositions
    }

    private fun getVerticalLayoutManager() : LinearLayoutManager?{
        val v_rv =  vertical_recyclerview?.layoutManager
        if (v_rv !=null)
            return v_rv as LinearLayoutManager
        else return null
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
                context?.startActivity(Intent(context,CategoriesActivity::class.java))
                activity?.overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
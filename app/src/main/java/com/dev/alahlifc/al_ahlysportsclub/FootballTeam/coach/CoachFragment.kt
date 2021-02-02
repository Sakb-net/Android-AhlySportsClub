package com.dev.alahlifc.al_ahlysportsclub.FootballTeam.coach


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.Coach.CoachActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentCoachBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber
import java.io.IOException


class CoachFragment : Fragment() {


    private lateinit var binding : FragmentCoachBinding
    private lateinit var viewModel: CoachesViewModel
    private  var adapter: CoachesAdapter? = null
    private lateinit var dialog: Dialog

    private var _hasLoadedOnce = false // your boolean field

    private var link : String ?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        link = arguments?.getString("link")
    }


    override fun onResume() {
        super.onResume()
        //register event bus
        EventBus.getDefault().register(this)
    }
    override fun onPause() {
        super.onPause()
        //unregister event bus
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    public fun onDataUpdated(message : String ) {

        Timber.e("VV EVENTBUS "+message)
        //  context?.toast("vvv "+message)

        val lang = PrefManager.getLanguage()
        viewModel.refresh(lang!!,message)  .observe(this, Observer {


            it.apply {
                when (this) {
                    is Resource.Progress -> {
                        if (loading)
                            dialog = context?.showLoadingDialog()!!
                        else
                            dialog.dismiss()
                    }
                    is Resource.Success -> {


                        Timber.e("xxxxxxxxxxx--"+data)


                        adapter?.updateDataset(data .data.coaches)

                        adapter?.onItemClick = {
                            context?.startActivity(
                                Intent(context, CoachActivity::class.java)
                                    .putExtra("obj",data.data.coaches[it]!!))

                        }

//                        if (data.data!= null ) {
//                            adapter = PlayersAdapter(data.data.players)
//                            if (binding.footballRv.adapter == null)
//                                binding.footballRv.adapter = adapter
//                            adapter.notifyDataSetChanged()
//                        }


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

    override fun setUserVisibleHint(isFragmentVisible_: Boolean) {
        super.setUserVisibleHint(isFragmentVisible_)

      //  context?.toast("visible "+ isFragmentVisible_)
        if (isFragmentVisible_ && !_hasLoadedOnce){
            getObservableData()

        }


      /**  if (this.isVisible) {
            context?.toast("visible now")
            // we check that the fragment is becoming visible
            if (!isFragmentVisible_ && !_hasLoadedOnce) {
                //run your async task here since the user has just focused on your fragment
                _hasLoadedOnce = true
                context?.toast("visible now + loaded once")
            }
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_coach, container, false)
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_coach, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CoachesViewModel::class.java)


    }


    private fun getObservableData() {
        val lang = PrefManager.getLanguage()

        viewModel.Players(lang!!,""+link)
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


                            _hasLoadedOnce = true

                            if (data.data!= null ) {
                                adapter = CoachesAdapter(data.data.coaches)
                                if (binding.coachesRv.adapter == null)
                                    binding.coachesRv.adapter = adapter
                            }

                            adapter?.onItemClick = {
                                context?.startActivity(
                                    Intent(context, CoachActivity::class.java)
                                        .putExtra("obj",data.data.coaches[it]!!))

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

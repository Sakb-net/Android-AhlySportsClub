package com.dev.alahlifc.al_ahlysportsclub.FootballTeam.player


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

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentPlayersBinding
import com.dev.alahlifc.al_ahlysportsclub.playerprofile.PlayerProfileActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber
import java.io.IOException

class PlayersFragment : Fragment() {

    private lateinit var binding : FragmentPlayersBinding
    private lateinit var viewModel: PlayersViewModel
     private lateinit var adapter: PlayersAdapter
     private lateinit var dialog: Dialog
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


                        adapter.updateDataset(data .data.players)

                        adapter.onItemClick = {
                            context?.startActivity(Intent(context,PlayerProfileActivity::class.java)
                                .putExtra("obj",data.data.players[it]!!))
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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_players, container, false)
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_players, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersViewModel::class.java)

        getObservableData()
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

                            Timber.e("called again!!!-------------------->")

                            if (data.data!= null ) {
                                adapter = PlayersAdapter(data.data.players)
                                if (binding.footballRv.adapter == null)
                                    binding.footballRv.adapter = adapter
                            }
                            adapter.onItemClick = {
                                context?.startActivity(Intent(context,PlayerProfileActivity::class.java)
                                    .putExtra("obj",data.data.players[it]!!))

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

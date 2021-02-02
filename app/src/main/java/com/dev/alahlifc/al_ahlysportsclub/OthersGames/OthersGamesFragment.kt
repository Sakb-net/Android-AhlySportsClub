package com.dev.alahlifc.al_ahlysportsclub.OthersGames


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentOthersGamesBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException

class OthersGamesFragment : Fragment() {

    private lateinit var binding : FragmentOthersGamesBinding
    private lateinit var viewModel: OthersGamesViewModel
    private lateinit var adapter: OthersGamesAdapter
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_others_games, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OthersGamesViewModel::class.java)
        getObservableData()
    }

    private fun getObservableData() {
        val lang = PrefManager.getLanguage()

        viewModel.OtherGames(lang!!)
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


                            adapter = OthersGamesAdapter(data.data)
                            if (binding.othersGamesRV.adapter == null)
                                binding.othersGamesRV.adapter = adapter

                            adapter.onItemClick = {
                                findNavController().apply {
                                    val bundle = Bundle().apply {
                                        // putInt("gallery_id", post.id)
                                        putString("title",data.data.get(it).name)

                                        if (!data.data.get(it).subteams.isNullOrEmpty())
                                        putString("link",""+data.data.get(it).subteams.get(0).link)

                                        putParcelable("obj",data.data.get(it))
                                    }
                                    navigate(R.id.action_othersGamesFragment_to_footballTeamFragment, bundle)
                                }

                               /* Navigation.findNavController(
                                    context as HomeActivity,
                                    R.id.bottom_navController
                                ).apply {
                                    val bundle = Bundle().apply {
                                        // putInt("gallery_id", post.id)
                                    }
                                    navigate(R.id.action_myProfileFragment_to_editProfileFragment, bundle)
                                }*/


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

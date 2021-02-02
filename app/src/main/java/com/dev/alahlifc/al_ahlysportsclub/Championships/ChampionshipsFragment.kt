package com.dev.alahlifc.al_ahlysportsclub.Championships


import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentChampionshipsBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException


class ChampionshipsFragment : Fragment() {

    private lateinit var binding : FragmentChampionshipsBinding
    private lateinit var viewModel: ChampionsViewModel
    private lateinit var adapter: ChampionsAdapter
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_championships, container, false)
      //  setHasOptionsMenu(true)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChampionsViewModel::class.java)
        getObservableData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.champion_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_search -> {
                context?.toast("search")
                showOrHideDialogFragment()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showOrHideDialogFragment() {
        var sheetFragment:BottomSearchDialogFragment ?= null
        if(sheetFragment == null){
            sheetFragment = BottomSearchDialogFragment()
        }
        sheetFragment.show(fragmentManager!!,sheetFragment.tag)
    }

    private fun getObservableData() {
        val lang = PrefManager.getLanguage()

        viewModel.Champions(lang!!)
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


                            adapter = ChampionsAdapter(data.data.data)
                            if (binding.championsRv.adapter == null)
                                binding.championsRv.adapter = adapter


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

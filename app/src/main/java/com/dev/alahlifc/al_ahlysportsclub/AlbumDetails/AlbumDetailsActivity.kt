package com.dev.alahlifc.al_ahlysportsclub.AlbumDetails

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.BigImage.BigImageActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityAlbumDetailsBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException

class AlbumDetailsActivity : LocalizationActivity() {

    private lateinit var binding: ActivityAlbumDetailsBinding
    private lateinit var viewModel: AlbumDetailsViewModel
    private lateinit var context : Context
    private lateinit var adapter: AlbumDetailsAdapter
    private lateinit var dialog: Dialog
    private var link : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album_details)

        context = this
        link = intent.getStringExtra("link")
        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()

        viewModel = ViewModelProviders.of(this).get(AlbumDetailsViewModel::class.java)
        getObservableData()
    }

    private fun getObservableData() {
        val lang = PrefManager.getLanguage()

        viewModel.AlbumDetails(lang!!,link)
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


                            val albums = data.subAlbums

                            adapter = AlbumDetailsAdapter(albums)
                            if (binding.albumDetailsRv.adapter == null)
                                binding.albumDetailsRv.adapter = adapter
                            adapter.onItemClick = {position ->
                                startActivity(Intent(this@AlbumDetailsActivity,BigImageActivity::class.java).apply {
                                    putExtra("obj",albums[position])
                                })
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



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@AlbumDetailsActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)



    }
}

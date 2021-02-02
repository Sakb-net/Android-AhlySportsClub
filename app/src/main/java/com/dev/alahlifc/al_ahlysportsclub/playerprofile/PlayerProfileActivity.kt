package com.dev.alahlifc.al_ahlysportsclub.playerprofile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityPlayerProfileBinding
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.models.mFootballTeam
import timber.log.Timber


class PlayerProfileActivity : LocalizationActivity() {

    private lateinit var binding: ActivityPlayerProfileBinding
    private var player : mFootballTeam.Player ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player_profile)

        player = intent.getParcelableExtra("obj")

      //  setContentView(R.layout.activity_player_profile)
      //  setSupportActionBar(toolbar)


        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()

        Timber.e("---> "+player.toString())
        binding.player = player
       // binding.executePendingBindings()

    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@PlayerProfileActivity,
            Constants.FONT_REGULAR,
            binding.name, binding.number, binding.weight, binding.age, binding.birth,
            binding.sport, binding.length, binding.center, binding.nationality)



    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_player_profile, menu)

        // return true so that the menu pop up is opened
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        else if (item.itemId == R.id.action_share){
            toast("share")
        }

        return super.onOptionsItemSelected(item)
    }

}

package com.dev.alahlifc.al_ahlysportsclub.Main

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.loadCircleImage
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityHomeBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import kotlinx.android.synthetic.main.nav_header_home.view.*
import com.google.android.material.navigation.NavigationView
import androidx.core.content.ContextCompat.getSystemService



class HomeActivity : LocalizationActivity(){

    private lateinit var navController : NavController
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        changeViewsFonts()
        initViews()

        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        changeNavigationFonts()

        NavigationUI.setupWithNavController(binding.navView, navController)



          val toggle = ActionBarDrawerToggle(
              this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
          )
       binding.drawerLayout.addDrawerListener(toggle)
       toggle.syncState()

        //binding.navView.setNavigationItemSelectedListener(this)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.toolbarTitle.text = controller.currentDestination?.label
        }
    }

    private fun initViews() {
      val user = PrefManager.getUser()
        binding.navView.getHeaderView(0).UserName_tv.text = user?.display_name
        binding.navView.getHeaderView(0).imageView.loadCircleImage( user?.image)


        if (user==null) {
            val nav_Menu = binding.navView.getMenu()
            nav_Menu.findItem(R.id.logOutFragment).setVisible(false)
        }

    }


    private fun changeViewsFonts() {
       // binding.navView.
        Util.changeViewTypeFace(this@HomeActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle, binding.navView.getHeaderView(0).UserName_tv)

    }
    private fun changeNavigationFonts(){
        Util.changeFontOfNavigation(this@HomeActivity,
            binding.navView)
    }

    override fun onBackPressed() {
        if ( binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
/**
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }*/
}

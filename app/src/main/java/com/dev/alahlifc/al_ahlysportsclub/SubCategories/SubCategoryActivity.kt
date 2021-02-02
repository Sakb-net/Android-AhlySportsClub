package com.dev.alahlifc.al_ahlysportsclub.SubCategories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.showDialog
import com.dev.alahlifc.al_ahlysportsclub.Cart.CartActivity
import com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory.ItemsInCategoryActivity
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivitySubCategoryBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mProductCategories
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import timber.log.Timber

class SubCategoryActivity : LocalizationActivity() {

    companion object badgeCount {
        var num =  0
    }
    override fun onResume() {
        super.onResume()
        num = PrefManager.getCartCount()!!
        invalidateOptionsMenu()
    }

    private lateinit var binding: ActivitySubCategoryBinding
    private lateinit var context : Context
    private lateinit var adapter : SubCategoriesAdapter
    private lateinit var textCartItemCount : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sub_category)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category)
       context = this
       // viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()
        val sub_element = intent.getParcelableExtra<mProductCategories.Data>("sub_category")
        binding.toolbarTitle.text =sub_element.name
            Timber.e("coming data is")

        num = PrefManager.getCartCount()!!
        invalidateOptionsMenu()


        adapter = SubCategoriesAdapter(sub_element.subcategories)
        if (binding.subCategoriesRv.adapter == null)
            binding.subCategoriesRv.adapter = adapter


        adapter.onItemClick= {
          //  toast("pos " + it)

            startActivity(
                Intent(context, ItemsInCategoryActivity::class.java)
                    .putExtra("category",sub_element.subcategories[it]?.name)
                    .putExtra("link",sub_element.subcategories[it]?.link)
            )
            overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
        }

    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@SubCategoryActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)



    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.champion_menu, menu)
    }*/


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.categories_menu, menu)

        val menuItem = menu?.findItem(R.id.action_cart);
        val actionView = menuItem?.actionView
        //MenuItemCompat.getActionView(menuItem)
        textCartItemCount = actionView?.findViewById(R.id.cart_badge) as TextView
        setupBadge()
        actionView?.setOnClickListener{
            onOptionsItemSelected(menuItem!!)
        }
        return true
    }

    private fun setupBadge() {

        if (textCartItemCount != null) {
            if (badgeCount.num == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE)
                }
            } else {
                textCartItemCount.setText((Math.min(badgeCount.num, 99)).toString())
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE)
                }
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        else if (item.itemId == R.id.action_cart) {
           // context.toast("Cart")
           // startActivity(Intent(this@SubCategoryActivity, CartActivity::class.java))
            val user = PrefManager.getUser()
            if (user==null){
                context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                    //   it ->it?.dismiss()
                    val intent = Intent(this , LoginActivity::class.java)
                    startActivity(intent)


                },{
                        it -> it?.dismiss()


                })}
            else{
                startActivity(Intent(this@SubCategoryActivity, CartActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }

}

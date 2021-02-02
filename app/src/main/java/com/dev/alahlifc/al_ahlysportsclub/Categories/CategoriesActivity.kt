package com.dev.alahlifc.al_ahlysportsclub.Categories

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityCategoriesBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException
import android.widget.TextView
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import kotlinx.android.synthetic.main.custom_cart_item_layout.view.*
import com.dev.alahlifc.al_ahlysportsclub.Cart.CartActivity
import com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory.ItemsInCategoryActivity
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.SubCategories.SubCategoryActivity


class CategoriesActivity : LocalizationActivity() {


    companion object badgeCount {
        var num =  0
    }

    override fun onResume() {
        super.onResume()
        num = PrefManager.getCartCount()!!
        invalidateOptionsMenu()
    }
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var context : Context
    private lateinit var adapter: CategoriesAdapter
    private lateinit var dialog: Dialog
    private lateinit var textCartItemCount : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_categories)
        context = this
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()
       // setContentView(R.layout.activity_categories)
        getObservableData()
    }

    private fun getObservableData() {
        val lang = PrefManager.getLanguage()
        val user = PrefManager.getUser()
        var token = ""
        if (user!=null)
         token = user?.access_token!!
        viewModel.Categories(token,lang!!,"20","0")
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading)
                                dialog = context.showLoadingDialog()
                            else
                                dialog.dismiss()
                        }
                        is Resource.Success -> {
                            // val mutableList =mutableListOf<Pair<String,mHome.Data?>?>()

                            num = data.count_cart
                            PrefManager.saveCartCount(num)
                            invalidateOptionsMenu()
                            adapter = CategoriesAdapter(data.data)
                            if (binding.categoriesRv.adapter == null)
                                binding.categoriesRv.adapter = adapter


                           adapter.onItemClick= {
                             //  toast("pos " + it)

                               if (data.data[it]?.subcategories?.isNotEmpty()!!) {


                                   startActivity(
                                       // Intent(context,ItemsInCategoryActivity::class.java)
                                       //   .putExtra("category",data.data[it]?.name)

                                       Intent(context, SubCategoryActivity::class.java)
                                          .putExtra("sub_category",data.data[it])
                                   )
                                   overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
                               } else {
                                   startActivity(
                                        Intent(context,ItemsInCategoryActivity::class.java)
                                          .putExtra("category",data.data[it]?.name)
                                            .putExtra("link",data.data[it]?.link)
                                   )
                                   overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit)
                               }
                           }
                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context.toast(getString(R.string.need_internet))
                            } else {
                                context.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@CategoriesActivity,
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
        textCartItemCount = actionView?.cart_badge as TextView
        setupBadge()
        actionView.setOnClickListener{
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
          //  context.toast("Cart")

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
                startActivity(Intent(this@CategoriesActivity, CartActivity::class.java))
            }



        }
        return super.onOptionsItemSelected(item)
    }


}

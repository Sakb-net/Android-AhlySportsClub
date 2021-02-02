package com.dev.alahlifc.al_ahlysportsclub.NewestProducts

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Cart.CartActivity
import com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory.BottomBuy.BottomBuyFragment
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.NewestProducts.NewestProductPagingAdapter.Companion.NETWORK_STATE_VIEW_TYPE
import com.dev.alahlifc.al_ahlysportsclub.NewestProducts.bottombuy.BottomBuyNewestFragment
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityNewestProductsBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mProduct
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.IOException

class NewestProductsActivity : LocalizationActivity() {
    companion object badgeCount {
        var num =  0
    }
    private lateinit var binding: ActivityNewestProductsBinding
    private lateinit var viewModel: NewestProductsViewModel
    private lateinit var context : Context
    private  var dialog: Dialog? = null
    private lateinit var adapter : NewestProductPagingAdapter
    private  var snackbar: Snackbar?= null
    private lateinit var textCartItemCount : TextView

    override fun onStop() {
        super.onStop()
        if (snackbar != null) {
            snackbar?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()


        num = PrefManager.getCartCount()!!
        invalidateOptionsMenu()
        //register event bus
        EventBus.getDefault().register(this)
    }
    override fun onPause() {
        super.onPause()
        //unregister event bus
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    public fun onCartUpdated(message : String ) {
        // toast(""+"updateddddd "+message)
        if (message== "cart_update"){
            num = PrefManager.getCartCount()!!
            invalidateOptionsMenu()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_newest_products)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_newest_products)
        context = this
        viewModel = ViewModelProviders.of(this).get(NewestProductsViewModel::class.java)


        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()


        viewModel = ViewModelProviders.of(this).get(NewestProductsViewModel::class.java)
        //  binding.newsRv.setHasFixedSize(true)

        adapter = NewestProductPagingAdapter(context!!)
       // binding.productsRv.layoutManager = LinearLayoutManager(context!!)
        if ( binding.productsRv.adapter == null)
            binding.productsRv.adapter = adapter


        viewModel.dataComing.observe(this, Observer {data->
            adapter.submitList(data)

            adapter.onItemClick= {
                //  toast("pos " + it)
                //showBottomSheetDialogFragment()
                val bottomSheetFragment = BottomBuyNewestFragment()
                val bundle = Bundle()
                bundle.putParcelable("newItem",data[it])
                bottomSheetFragment.arguments = bundle
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())

            }



        })

        setupRV()

        handleObserverState()
        viewModel.onGetMyListingRequested("")


    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@NewestProductsActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.categories_menu, menu)

        val menuItem = menu?.findItem(R.id.action_cart)
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
            //  context.toast("Cart")
            //startActivity(Intent(this@ItemsInCategoryActivity, CartActivity::class.java))
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
                startActivity(Intent(this@NewestProductsActivity, CartActivity::class.java))
            }



        }
        return super.onOptionsItemSelected(item)
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // handle arrow click here
//        if (item.itemId == android.R.id.home) {
//            onBackPressed()
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    private fun setupRV() {
        val columns = 2
        val layoutManager = GridLayoutManager(context, columns)
        layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)) {
                    NETWORK_STATE_VIEW_TYPE -> columns
                    else -> 1
                }
            }
        }
        binding.productsRv.layoutManager = layoutManager
    }


    private fun handleObserverState(){
        viewModel.initialResult.observe(this, Observer {


            it.apply {
                when (this) {
                    is Resource.Progress -> {
                        if (loading)
                            dialog = context?.showLoadingDialog()!!
                        else
                            dialog?.dismiss()
                    }
                    is Resource.Success -> {
                        dialog?.dismiss()
                        ///  none.visibility = View.GONE
                    }
                    is Resource.Failure -> {
                        if (e is IOException) {
                            //context?.toast("Need Internet to Work ")
                            dialog?.dismiss()
                            snackbar = context?.showConnectionErrorView({

                                viewModel.onGetMyListingRequested("") })



                        } else {

                            if(e.message.isNullOrEmpty())
                                context?.toast(getString(R.string.something_wrong))
                            else{
                                if(e.message == "no data"){
                                    dialog?.dismiss()
                                    ///  none.visibility = View.VISIBLE
                                }
                                else
                                    context?.toast(getString(R.string.something_wrong))
                                    //context?.toast(e.message.toString())
                            }}
                    }


                } } })



        // after load more callback ...
        viewModel.networkStatus.observe(this, Observer {
            adapter.networkStatus = it
            when (it) { // preview snack bar with retry option ....
                NetworkStatus.ERROR -> context?.showConnectionErrorView({
                    viewModel.onRetryGettingPagedShows()

                })
                else -> Unit
            }
        })


    }
}

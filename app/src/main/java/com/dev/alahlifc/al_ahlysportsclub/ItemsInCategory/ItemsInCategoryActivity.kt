package com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory

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
import com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory.ItemsInCategoryPagingAdapter.Companion.NETWORK_STATE_VIEW_TYPE
import com.dev.alahlifc.al_ahlysportsclub.ItemsInCategory.SortBy.BottomSortFragment
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityItemsInCategoryBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.IOException

class ItemsInCategoryActivity : LocalizationActivity() {
    companion object badgeCount {
        var num =  0
    }
    private lateinit var binding: ActivityItemsInCategoryBinding
    private lateinit var viewModel: ItemsInCategoryViewModel
    private lateinit var context : Context
    private  var dialog: Dialog? = null
    private lateinit var adapter : ItemsInCategoryPagingAdapter
    private  var snackbar: Snackbar?= null
    private lateinit var textCartItemCount : TextView
    private var link :String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items_in_category)
        context = this
        viewModel = ViewModelProviders.of(this).get(ItemsInCategoryViewModel::class.java)
        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.toolbarTitle.text = intent.getStringExtra("category")
        changeViewsFonts()
        num = PrefManager.getCartCount()!!
        invalidateOptionsMenu()
        link = intent.getStringExtra("link")
        // setContentView(R.layout.activity_categories)
       // getObservableData()
        adapter = ItemsInCategoryPagingAdapter(context!!)
        if ( binding.itemCategoriesRv.adapter == null)
            binding.itemCategoriesRv.adapter = adapter
        setupRV()
        initObserving("ID")
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

    private fun initObserving(sort_type : String){
        viewModel.dataComing.observe(this, Observer { data ->
            //   toast(""+data.size)
        //    binding.productsNum.text = ""+data.size+" "+getString(R.string.products_)
            adapter.submitList(data)
            adapter.onItemClick= {
                //  toast("pos " + it)
                //showBottomSheetDialogFragment()
                val bottomSheetFragment = BottomBuyFragment()
                val bundle = Bundle()
                bundle.putParcelable("item",data[it])
                bottomSheetFragment.arguments = bundle
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())

            }
        })
        handleObserverState(sort_type)
        viewModel.onGetMyListingRequested(false,link, sort_type)
        binding.sortbyBtn.setOnClickListener {
            val bottomSheetFragment = BottomSortFragment()
            bottomSheetFragment.onItemClick={
               // toast(""+it)

                if (it==2)

                    viewModel.onGetMyListingRequested(true,link, /*"rate"*/"ID")
                else if(it==3)
                 viewModel.onGetMyListingRequested(true,link, "price_desc")
                else if(it == 4)
                     viewModel.onGetMyListingRequested(true,link, "price_asc")
              //  viewModel.refresh("price_desc")
            }
            val bundle = Bundle()
            //   bundle.putParcelable("item",data.data.products[it])
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())
        }
    }
    /*   private fun getObservableData() {
        val lang = PrefManager.getLanguage()
        viewModel.ItemsInCategories(lang!!,link)
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

                            adapter = ItemsInCategoryAdapter(data.data.products)
                            if (binding.itemCategoriesRv.adapter == null)
                                binding.itemCategoriesRv.adapter = adapter

                            adapter.onItemClick= {
                                //  toast("pos " + it)

                                //showBottomSheetDialogFragment()
                                val bottomSheetFragment = BottomBuyFragment()

                                 val bundle = Bundle()
                                bundle.putParcelable("item",data.data.products[it])
                                bottomSheetFragment.arguments = bundle
                                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())

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
    }*/
    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@ItemsInCategoryActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle,
            binding.productsNum)



    }
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
                startActivity(Intent(this@ItemsInCategoryActivity, CartActivity::class.java))
            }



        }
        return super.onOptionsItemSelected(item)
    }
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
        binding.itemCategoriesRv.layoutManager = layoutManager
    }
    private fun handleObserverState(sort_type: String){
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

                                viewModel.onGetMyListingRequested(false,link, sort_type) })



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


        viewModel.productCountResult.observe(this, Observer {
           // it.apply {

                binding.productsNum.text = ""+it+" "+getString(R.string.products_)


             // }

        })



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

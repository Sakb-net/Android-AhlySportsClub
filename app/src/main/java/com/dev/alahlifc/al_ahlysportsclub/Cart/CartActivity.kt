package com.dev.alahlifc.al_ahlysportsclub.Cart

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityCartBinding
import com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.activity.CheckoutUIActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException

class CartActivity : LocalizationActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var context : Context
    private lateinit var adapter: CartItemsAdapter
    private lateinit var dialog: Dialog




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        context = this
        viewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)
        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()
        binding.buttonRegister.setOnClickListener {


            context.showCartDialog(""+binding.totalPrice.text.toString(),
                {
                it?.dismiss()
                getCheckoutID()


            })


        }
        getObservableData()
    }



    private fun getObservableData() {
        val user = PrefManager.getUser()
        viewModel.Cart(user?.access_token!!)
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
                          //  context.toast(""+data.statusCode)
                            if (data.statusCode==11) {
                                //  context?.toast("status " + data.statusCode)
                                PrefManager.saveUser(null)
                                val logoutIntent = Intent(context, LoginActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                context?.startActivity(logoutIntent)
                                finish()
                                return@Observer

                            }


                            binding.productsNum.text = ""+data.data.productCart.size.toString() +" "+getString(R.string.product)
                         binding.totalPrice.text   = ""+data.data.totalPriceCart + " "+ getString(R.string.rs)
                          data.cartFees.map {
                              if(it.typePrice=="value")
                              binding.feesArr.text = ""+ binding.feesArr.text+""+it.name+"   "+it.price+" "+ getString(R.string.rs)+"\n"
                              else
                                  binding.feesArr.text = ""+ binding.feesArr.text+""+it.name+"   "+it.price+" % \n"

                          }


                            adapter = CartItemsAdapter(data.data.productCart)
                            if (binding.cartRv.adapter == null)
                                binding.cartRv.adapter = adapter

                            adapter.onItemClick= {
                                //  toast("pos " + it)

                                deleteFromCart(data.data.productCart[it]?.link!!,it)
                            }
                             /**   //showBottomSheetDialogFragment()
                                val bottomSheetFragment = BottomBuyFragment()

                                val bundle = Bundle()
                                bundle.putParcelable("item",data.data.products[it])
                                bottomSheetFragment.arguments = bundle
                                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())

                            }*/


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


    private fun deleteFromCart(link : String, pos : Int){


        val user = PrefManager.getUser()

        val lang = PrefManager.getLanguage()

        viewModel.DeleteCart(user?.access_token!!,link,lang!!)
            .observe(this, Observer {


                it.apply {
                    when (this) {
                        is Resource.Progress -> {
                            if (loading);
                            /*    dialog = context?.showLoadingDialog()!!
                            else
                                dialog.dismiss()*/
                        }
                        is Resource.Success -> {

                            adapter.removeAt(pos)
                            binding.productsNum.text = ""+data.data.productCart.size.toString() +" "+getString(R.string.product)
                            binding.totalPrice.text   = ""+data.data.totalPriceCart + " "+ getString(R.string.rs)

                            PrefManager.saveCartCount(data.data.productCart.size)
                            // context?.toast(""+data.message)
                            // Timber.e(""+data.data.toString())

                         //   context?.toast(""+data.message)


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

  private  fun getCheckoutID(){


        val user = PrefManager.getUser()

        val lang = PrefManager.getLanguage()

        viewModel.Checkout(user?.access_token!!,lang!!)
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

                          //  adapter.removeAt(pos)
                          //  binding.productsNum.text = ""+data.data.productCart.size.toString() +" "+getString(R.string.product)
                            /// context?.toast(""+data.message)
                            context?.startActivity(Intent(context, CheckoutUIActivity::class.java)
                                .putExtra("checkout_id",data.data.checkoutId)
                                .putExtra("result_url",data.data.shopperResultUrl)
                                .putExtra("total",  binding.totalPrice.text )

                            )

                            // Timber.e(""+data.data.toString())

                            //   context?.toast(""+data.message)


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



    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@CartActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle,
            binding.productsNum,
            binding.details,
            binding.buttonRegister,
            binding.feesArr
            )

        Util.changeViewTypeFace(this@CartActivity,
            Constants.FONT_BOLD,
            binding.textView14,
            binding.totalPrice
        )



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}

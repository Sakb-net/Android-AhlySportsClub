package com.dev.alahlifc.al_ahlysportsclub.ProductDetails

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.TabLayoutMediator
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.ProductDetails.bottomsheetbuy.BottomBuyDetailsFragment
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityProductDetailsBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mProductSingle
import com.dev.alahlifc.al_ahlysportsclub.rate.RatesActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.google.android.material.tabs.TabLayout
import org.jetbrains.annotations.NotNull
import java.io.IOException

class ProductDetailsActivity : LocalizationActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var viewModel: ProductDetailsViewModel
    private lateinit var context : Context
    private var dialog: Dialog? = null
    private var link : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        val window = window
        val winParams = window.attributes
        winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        window.attributes = winParams
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        context = this
        viewModel = ViewModelProviders.of(this).get(ProductDetailsViewModel::class.java)
        link = intent.getStringExtra("link")?:""
        getObservableData(link)
        binding.back.setOnClickListener {
            onBackPressed()
        }

    }

    private fun getObservableData(link: String) {
        val lang = PrefManager.getLanguage()



        viewModel.productDetails(lang!!,link)
            .observe(this, Observer {


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


                            binding.positiveBtn.setOnClickListener {
                                val bottomSheetFragment = BottomBuyDetailsFragment()
                                val bundle = Bundle()
                                bundle.putParcelable("detailsItem",data.data)
                                bottomSheetFragment.arguments = bundle
                                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())
                            }

                            binding.rates.setOnClickListener{
                                startActivity(Intent(this@ProductDetailsActivity,RatesActivity::class.java)
                                    .putExtra("link",""+data.data.link))
                            }

                            val res = data.data.anotherImage
                            res.add(0, mProductSingle.Data.AnotherImage(""+data.data.image))
                            if (binding.viewpagerSlider.adapter == null)
                                binding.viewpagerSlider.adapter = ProductDetailsSliderAdapter(res)

                            binding.product = data.data


                            val tabLayoutMediator =
                                TabLayoutMediator( binding.indicator, binding.viewpagerSlider, true,
                                    object : TabLayoutMediator.OnConfigureTabCallback {
                                        override fun onConfigureTab(@NotNull tab: TabLayout.Tab, position: Int) {
                                            // position of the current tab and that tab
                                        }
                                    })

                            tabLayoutMediator.attach()


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
}

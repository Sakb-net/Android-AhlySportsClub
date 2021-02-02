package com.dev.alahlifc.al_ahlysportsclub.Fees

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.showEnterPrintNameDialog
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivitySelectFeesBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mItemsInCategory
import timber.log.Timber
import android.app.Activity
import android.content.Intent
import com.dev.alahlifc.al_ahlysportsclub.models.mProduct
import com.dev.alahlifc.al_ahlysportsclub.models.mProductSingle


class SelectFeesActivity : LocalizationActivity() {
    private lateinit var binding: ActivitySelectFeesBinding
    private  var args : mItemsInCategory.Product? = null
    private  var newItemArgs  : mProduct.Data? = null
    private  var detailsItem  : mProductSingle.Data? = null

    private var  adapter : selectFeesAdapter? = null
    private var  adapterNewest : selectFeesNewestAdapter? = null
    private var  adapterDetails : selectFeesDetailsAdapter? = null
    private var  printName  : String? = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_fees)
        args = intent.getParcelableExtra<mItemsInCategory.Product>("fees")
        newItemArgs = intent.getParcelableExtra<mProduct.Data>("feesNewest")
        detailsItem = intent.getParcelableExtra<mProductSingle.Data>("detailsItem")


        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()

        if (args!=null) {
            adapter = selectFeesAdapter(args!!.fees as ArrayList<mItemsInCategory.Fee>)

            if (binding.feesRv.adapter == null)
                binding.feesRv.adapter = adapter

            adapter?.onItemClick = { isButton, position ->
                if (isButton==1){
                    showEnterPrintNameDialog({dialog, name ->
                        //   toast(""+name)
                        printName = name
                        dialog?.dismiss()

                    })

                }
            }


        }


        else if(detailsItem != null) {
            adapterDetails = selectFeesDetailsAdapter(detailsItem!!.fees as ArrayList<mProductSingle.Data.Fee>)


            if (binding.feesRv.adapter == null)
                binding.feesRv.adapter = adapterDetails

            adapterNewest?.onItemClick = { isButton, position ->
                if (isButton==1){
                    showEnterPrintNameDialog({dialog, name ->
                        //   toast(""+name)
                        printName = name
                        dialog?.dismiss()

                    })

                }
            }


        }




        else {
            adapterNewest = selectFeesNewestAdapter(newItemArgs!!.fees as ArrayList<mProduct.Fee>)


            if (binding.feesRv.adapter == null)
                binding.feesRv.adapter = adapterNewest

            adapterNewest?.onItemClick = { isButton, position ->
                if (isButton==1){
                    showEnterPrintNameDialog({dialog, name ->
                        //   toast(""+name)
                        printName = name
                        dialog?.dismiss()

                    })

                }
            }


        }




    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.fees_menu, menu)
        return true
    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            val returnIntent = Intent()
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()

           // onBackPressed()
        }
        else if (item.itemId == R.id.action_save) {

            if (adapter!=null) {
                val dataSet = adapter?.getListAfterEdit()
                Timber.e("" + dataSet.toString())

                val returnIntent = Intent()
                returnIntent.putExtra("result", dataSet)
                returnIntent.putExtra("printName", printName)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            else {
                val dataSett = adapterNewest?.getListAfterEdit()
                Timber.e("" + dataSett.toString())

                val returnIntent = Intent()
                returnIntent.putExtra("result", dataSett)
                returnIntent.putExtra("printName", printName)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }






            //  context.toast("Cart")
           // startActivity(Intent(this@CategoriesActivity, CartActivity::class.java))

        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@SelectFeesActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle
           )



    }
}

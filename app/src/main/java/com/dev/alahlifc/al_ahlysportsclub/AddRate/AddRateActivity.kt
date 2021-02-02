package com.dev.alahlifc.al_ahlysportsclub.AddRate

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityAddRateBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException

class AddRateActivity : LocalizationActivity() {

    private lateinit var binding: ActivityAddRateBinding
    private lateinit var viewModel: AddRateViewModel
    private lateinit var context : Context
    private lateinit var dialog: Dialog
    private var link : String = ""
    private var rate : String = ""
    private var video_link : String ?= ""
    private var type_pro : String ?= ""
    private var reply : Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_rate)
        context = this
        link = intent.getStringExtra("link")
        reply = intent.getBooleanExtra("reply", false)
        video_link = intent.getStringExtra("video_link")
        type_pro = intent.getStringExtra("type")
        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()

        viewModel = ViewModelProviders.of(this).get(AddRateViewModel::class.java)
        binding.ratingbar.setOnRatingChangeListener {
          //  toast(""+it)

            rate = ""+(it.toInt())
        }

        binding.sendCmntBtn.setOnClickListener {

            if (rate.length==0){
                context?.toast(getString(R.string.empty_comment_rate))
                return@setOnClickListener
            }



            if (binding.editText.text.trim().length==0){
                context?.toast(getString(R.string.empty_comment_msh))

            }
            else {
                val userr = PrefManager.getUser()
                if (!reply) {
                    if (userr!=null) {

                        val lang = PrefManager.getLanguage()

                        var token = ""
                        var ownerName = ""

                        if ((binding.name.text.trim()).length == 0){

                            token = userr?.access_token!!
                        }
                        else{
                            ownerName = binding.name.text.toString()
                        }

                        getObservableData(token,lang!!,ownerName, userr?.email,type_pro!!)



                    }
                    else {
                        val lang = PrefManager.getLanguage()
                        var token = ""
                        var  ownerName = binding.name.text.toString()


                        getObservableData(token,lang!!,ownerName, binding.email.text.toString(),type_pro!!)




                    }
                }
                else{
                    if (userr!=null) {
                        val lang = PrefManager.getLanguage()

                        var token = ""
                        var ownerName = ""

                        if ((binding.name.text.trim()).length == 0){

                            token = userr?.access_token!!
                        }
                        else{
                            ownerName = binding.name.text.toString()
                        }
                        getObservableReply(token,lang!!,ownerName, userr?.email,type_pro!!)
                    }else{

                        val lang = PrefManager.getLanguage()
                        var token = ""
                        var  ownerName = binding.name.text.toString()
                        getObservableReply(token,lang!!,ownerName, binding.email.text.toString(),type_pro!!)


                    }
                }
            }
        }
    }
    private fun getObservableData(token : String,lang:String,ownerName : String,email : String, type : String) {



        if (token.trim().length==0&&ownerName.trim().length==0){
            context?.toast(getString(R.string.empty_comment_name))
            return

        }
        if (token.trim().length==0&&email.trim().length==0){
            context?.toast(getString(R.string.empty_comment_email))
            return

        }

        viewModel.AddComent(token,lang!!,binding.editText.text.toString(),link,email,ownerName,type,rate)
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


                            context?.toast(""+data.message)
                            val returnIntent = Intent()
                            returnIntent.putExtra("result", ""+binding.editText.text.toString())
                            returnIntent.putExtra("obj",data )
                            if (ownerName.trim().length>0)
                                returnIntent.putExtra("name", ""+ownerName)
                            setResult(Activity.RESULT_OK, returnIntent)
                            this@AddRateActivity.finish()

                            // onBackPressed()


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
    private fun getObservableReply(token : String,lang:String,ownerName : String,email : String, type: String) {


        if (token.trim().length==0&&ownerName.trim().length==0){
            context?.toast(getString(R.string.empty_comment_name))
            return

        }
        if (token.trim().length==0&&email.trim().length==0){
            context?.toast(getString(R.string.empty_comment_email))
            return

        }
        viewModel.AddReply(token,lang!!,binding.editText.text.toString(),video_link!!,link,email,ownerName,type )
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


                            context?.toast(""+data.message)
                            val returnIntent = Intent()
                            returnIntent.putExtra("result", ""+binding.editText.text.toString())
                            if (ownerName.trim().length>0)
                                returnIntent.putExtra("name", ""+ownerName)
                            setResult(Activity.RESULT_OK, returnIntent)
                            this@AddRateActivity.finish()

                            // onBackPressed()


                        }
                        is Resource.Failure -> {
                            if (e is IOException) {
                                context?.toast(getString(R.string.need_internet))
                            }

                            else {
                                context?.toast(getString(R.string.something_wrong))
                            }
                        }
                    }
                }
            })
    }
    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@AddRateActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle, binding.sendCmntBtn, binding.editText, binding.name)



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}

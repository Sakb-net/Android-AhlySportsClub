package com.dev.alahlifc.al_ahlysportsclub.AddComment

import android.app.Dialog
import android.content.Context
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
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityAddCommentBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import timber.log.Timber
import java.io.IOException
import android.app.Activity
import android.content.Intent



class AddCommentActivity : LocalizationActivity() {

    private lateinit var binding: ActivityAddCommentBinding
    private lateinit var viewModel: AddCommentViewModel
    private lateinit var context : Context
    private lateinit var dialog: Dialog
    private var link : String = ""
    private var video_link : String ?= ""
    private var type_pro : String ?= ""
    private var reply : Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_comment)
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

        viewModel = ViewModelProviders.of(this).get(AddCommentViewModel::class.java)
      binding.sendCmntBtn.setOnClickListener {
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

        viewModel.AddComent(token,lang!!,binding.editText.text.toString(),link,email,ownerName,type)
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
                            this@AddCommentActivity.finish()

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
                            this@AddCommentActivity.finish()

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
        Util.changeViewTypeFace(this@AddCommentActivity,
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

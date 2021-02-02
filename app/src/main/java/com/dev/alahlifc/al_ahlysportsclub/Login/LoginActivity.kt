package com.dev.alahlifc.al_ahlysportsclub.Login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast
import com.dev.alahlifc.al_ahlysportsclub.ForgotPassword.ForgotPassActivity
import com.dev.alahlifc.al_ahlysportsclub.Main.HomeActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Register.RegisterActivity
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityLoginBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import timber.log.Timber
import java.io.IOException

class LoginActivity : LocalizationActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var context : Context
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        context = this
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        changeViewsFonts()
        binding.buttonLogin.setOnClickListener {

            Timber.e("button login clicked")
            //  this.toast("clicked")


            val userName = binding.UserNameEt.text.toString()
            val password =  binding.PasswordEt.text.toString()



            Timber.e("val is $userName -- $password ")

            if(validateLoginFields(userName,password))
               performLogin(userName,password)

        }


        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
           // this.toast("pos "+isChecked)
            PrefManager.saveRememberMeFlag(isChecked)
        }


        binding.signUp.setOnClickListener {
            startActivity(Intent(this , RegisterActivity::class.java))
        }
        binding.tvForgotPass.setOnClickListener {
            startActivity(Intent(this , ForgotPassActivity::class.java))
        }
    }

    private fun performLogin(userName_or_email: String, password: String) {
        viewModel.login(email=userName_or_email, password=password,fcm_token = "")

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

                            if(data.StatusCode==1){ // success


                                PrefManager.saveUser(data.data)

                                // to reGetTheToken
                                PrefManager.saveUserName_or_email(userName_or_email)
                                PrefManager.savePassword(password)

                                startActivity(
                                    Intent(this@LoginActivity, HomeActivity::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                                )
                                //  overridePendingTransition(R.anim.push_right_enter, R.anim.push_right_exit)


                            }

                            else{
                                context.toast(""+data.Message)
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

    private fun validateLoginFields(userName: String, password: String): Boolean {

        if(TextUtils.isEmpty(userName)) {
            binding.UserNameEt.error = getString(R.string.required_field)
            return false
        }
        if(TextUtils.isEmpty(password)) {
            binding.PasswordEt.error = getString(R.string.required_field)
            return false
        }
        else return true
    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@LoginActivity, Constants.FONT_REGULAR,
            binding.textView3, binding.buttonLogin, binding.tvForgotPass,binding.signUp,binding.textView4, binding.textView2)

    }

}

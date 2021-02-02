package com.dev.alahlifc.al_ahlysportsclub.LogOut


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dev.alahlifc.al_ahlysportsclub.Base.showDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.Base.toast

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import java.io.IOException
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager


class LogOutFragment : Fragment() {
    private lateinit var viewModel: LogoutViewModel
    private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_out, container, false)
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LogoutViewModel::class.java)
        context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logout), {
                it ->it?.dismiss()
            performLogout()

            },{
                it -> it?.dismiss()
               findNavController().navigateUp()

            })
    }

    private fun performLogout() {
        val user = PrefManager.getUser()
        viewModel.logout(user?.access_token!!)
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


                          //  if (data.statusCode==1){
                                PrefManager.saveUser(null)
                                val logoutIntent = Intent(context, LoginActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                context?.startActivity(logoutIntent)
                                activity?.finish()
                          //  }

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


}

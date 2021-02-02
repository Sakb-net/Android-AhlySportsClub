package com.dev.alahlifc.al_ahlysportsclub.ContactUs


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentContactUsBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException


class ContactUsFragment : Fragment() {

    private lateinit var binding : FragmentContactUsBinding
    private lateinit var viewModel: ContactUsViewModel
    private lateinit var dialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact_us, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        changeViewsFonts()
        viewModel = ViewModelProviders.of(this).get(ContactUsViewModel::class.java)

        binding.buttonSend.setOnClickListener {
                if(TextUtils.isEmpty(binding.MessageContentEt.text)) {
                    binding.MessageContentEt.error = getString(R.string.required_field)
                }
            else {

                    val user = PrefManager.getUser()
                    if(user!=null){ viewModel.addContact(
                        user?.access_token!!
                        , binding.MessageContentEt.text.toString(),
                        binding.UserNameEt.text.toString(),
                        binding.EmailEt.text.toString()
                    )
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

                                        context?.toast("" + data.message)


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

                    else{
                        context?.showDialog(R.drawable.dialog_warning,getString(R.string.are_sure_logIn), {
                            //   it ->it?.dismiss()
                            val intent = Intent(activity , LoginActivity::class.java)
                            startActivity(intent)


                        },{
                                it -> it?.dismiss()
                            findNavController().navigateUp()

                        })
                    }


                }
        }

        viewModel.contact()
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

                            if (data.statusCode==1){

                                binding.contactData = data.data

                            }

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
        Util.changeViewTypeFace(context,
            Constants.FONT_REGULAR,
            binding.tvTiteleContactus,
            binding.buttonSend,
            binding.UserNameEt,
            binding.EmailEt,
            binding.MessageTitleEt,
            binding.MessageContentEt)

    }


}

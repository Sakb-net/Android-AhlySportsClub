package com.dev.alahlifc.al_ahlysportsclub.MyProfile


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dev.alahlifc.al_ahlysportsclub.Base.*
import com.dev.alahlifc.al_ahlysportsclub.EditProfile.EditProfileActivity
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity

import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource
import com.dev.alahlifc.al_ahlysportsclub.databinding.FragmentMyProfileBinding
import com.dev.alahlifc.al_ahlysportsclub.models.mMyProfile
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import java.io.IOException


class MyProfileFragment : Fragment() {

    private lateinit var binding : FragmentMyProfileBinding
    private lateinit var viewModel: MyProfileViewModel
    private lateinit var dialog: Dialog
    private var userInfo : mMyProfile.Data? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_my_profile, container, false)
        setHasOptionsMenu(true)
        changeViewsFonts()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
        val user = PrefManager.getUser()
         if (user!=null)
        viewModel.fillProfile(user!!.access_token)
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

                           // context?.toast(""+data.message)
                            if (data.statusCode==11) {
                              //  context?.toast("status " + data.statusCode)
                                PrefManager.saveUser(null)
                                val logoutIntent = Intent(context, LoginActivity::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                context?.startActivity(logoutIntent)
                                activity?.finish()
                            }

                            else {
                                userInfo = data.data
                                binding.myProfileData = userInfo
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_profile_menu, menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_edit -> {
                //context?.toast("open edit")
                val intent = Intent(activity , EditProfileActivity::class.java)
                intent.putExtra("myProfile_key",userInfo)
                 activity?.startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun changeViewsFonts() {
        Util.changeViewTypeFace(context, Constants.FONT_BOLD, binding.personNameTv,
            binding.personPhoneTv,binding.personEmailTv,binding.personAddressTv)

        Util.changeViewTypeFace(context, Constants.FONT_REGULAR, binding.nameTitleTv,
            binding.phoneTitleTv,binding.emailTitleTv,binding.addressTitleTv
            /*,binding.textView7*/)



    }
}

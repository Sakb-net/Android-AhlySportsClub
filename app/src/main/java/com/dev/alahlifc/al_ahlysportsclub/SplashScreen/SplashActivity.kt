package com.dev.alahlifc.al_ahlysportsclub.SplashScreen

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Main.HomeActivity
import com.dev.alahlifc.al_ahlysportsclub.Login.LoginActivity
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivitySplashBinding
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import timber.log.Timber
import java.lang.ref.WeakReference
import android.view.animation.Animation
import android.view.animation.AnimationUtils


class SplashActivity : LocalizationActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var fade0: Animation? = null
    private var uptodown: Animation? = null
    private var downtoup:Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setDefaultLanguage("ar")
        PrefManager.saveLanguage("ar")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        changeViewsFonts()

        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup)


        fade0 = AnimationUtils.loadAnimation(this, R.anim.fade_in_enter)
        binding.imageView.startAnimation(fade0)


        fade0?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                binding.textView.setAnimation(downtoup)
            }

            override fun onAnimationEnd(animation: Animation) {

                if(PrefManager.getRememberMeFlag()==true && PrefManager.getUser()!=null){
                    Timber.e("token "+PrefManager.getUser().toString())
                    val intent = Intent(this@SplashActivity , HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                }else{

                    val intent = Intent(this@SplashActivity , HomeActivity::class.java)
                   // val intent = Intent(this@SplashActivity , LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)


                }



                overridePendingTransition(0, 0)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

       // MyTask(this).execute()

    }

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@SplashActivity, Constants.FONT_REGULAR, binding.textView)

    }

    private class MyTask internal constructor(context: SplashActivity) : AsyncTask<Void, Void, String>() {

            private val activityReference: WeakReference<SplashActivity> = WeakReference(context)

            override fun doInBackground(vararg params: Void): String {

                // do some long running task...
                try {
                    Thread.sleep(3000)
                } catch (e: Exception) {
                }

                return "task finished"
            }

            override fun onPostExecute(result: String) {

                // get a reference to the activity if it is still there
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return


                if(PrefManager.getRememberMeFlag()==true && PrefManager.getUser()!=null){
                    Timber.e("token "+PrefManager.getUser().toString())
                    val intent = Intent(activity , HomeActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                }else{

                    val intent = Intent(activity , LoginActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()

                }


            }
        }
    }






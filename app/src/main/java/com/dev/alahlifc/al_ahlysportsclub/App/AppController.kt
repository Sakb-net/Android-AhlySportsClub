package com.dev.alahlifc.al_ahlysportsclub.App

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import timber.log.Timber
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.dev.alahlifc.al_ahlysportsclub.BuildConfig
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader


class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        BigImageViewer.initialize(GlideImageLoader.with(this))

        PrefManager.initialize(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /*
    localization ....
     */
    var localizationDelegate = LocalizationApplicationDelegate(this)

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localizationDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

   /** companion object {
        fun setLocale(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            context.applicationContext.resources.updateConfiguration(config, null)
            context.saveLanguage(language)
        }*/



    //}
}
package com.dev.alahlifc.al_ahlysportsclub.BookTicket

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.dev.alahlifc.al_ahlysportsclub.Base.Constants
import com.dev.alahlifc.al_ahlysportsclub.Base.Util
import com.dev.alahlifc.al_ahlysportsclub.Base.showLoadingDialog
import com.dev.alahlifc.al_ahlysportsclub.R
import com.dev.alahlifc.al_ahlysportsclub.databinding.ActivityBookTicketBinding
import timber.log.Timber

class BookTicketActivity : LocalizationActivity() {

    private lateinit var binding: ActivityBookTicketBinding
    private lateinit var dialog: Dialog
    private var paymentWebView: WebView? = null

    private fun changeViewsFonts() {
        Util.changeViewTypeFace(this@BookTicketActivity,
            Constants.FONT_REGULAR,
            binding.toolbarTitle)



    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_ticket)

        binding.toolbar.setTitle("")
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        changeViewsFonts()


        paymentWebView =  WebView(applicationContext)
        binding.webContainer.addView(paymentWebView)

        val link = intent.getStringExtra("link")
        Timber.e("link--- "+link)
        paymentWebView?.loadUrl(link)
         val webSettings = paymentWebView?.settings
         webSettings?.javaScriptEnabled = true
        paymentWebView?.webViewClient=client

        dialog = this@BookTicketActivity?.showLoadingDialog()!!
    }

    private var client: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            dialog?.dismiss()
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        if(binding.webContainer!=null)
            binding.webContainer.removeAllViews()
        if (paymentWebView != null) {

            paymentWebView!!.setTag(null)

            paymentWebView!!.clearHistory()

            paymentWebView!!.removeAllViews()

            paymentWebView!!.clearView()

            paymentWebView!!.webViewClient = null

            paymentWebView!!.destroy()
        }}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}

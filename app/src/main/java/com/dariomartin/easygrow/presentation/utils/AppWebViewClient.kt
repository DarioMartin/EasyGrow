package com.dariomartin.easygrow.presentation.utils

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class AppWebViewClient(private val progressBar: ProgressBar) : WebViewClient() {

    init {
        progressBar.visibility = View.VISIBLE
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
    }


}

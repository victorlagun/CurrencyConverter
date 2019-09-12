package com.scan.currencyconverter.util

import android.content.Context
import android.net.ConnectivityManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan


fun setSpan(formattedRate: String, textHigh: Float, color: Int): Spannable {
    val result = SpannableString(formattedRate)
    result.setSpan(
        RelativeSizeSpan(textHigh), 0,
        formattedRate.substringBefore('.', formattedRate).length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    result.setSpan(
        ForegroundColorSpan(color), 0,
        formattedRate.substringBefore('.', formattedRate).length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return result
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return run {
        val activeNetwork = connectivityManager.activeNetworkInfo
        activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}
package com.scan.currencyconverter.util

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
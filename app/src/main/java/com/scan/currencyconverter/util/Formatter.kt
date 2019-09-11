package com.scan.currencyconverter.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class Formatter(pattern: String) {
    private val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    private val symbols = formatter.decimalFormatSymbols

    init {
        formatter.applyPattern(pattern)
        formatter.decimalFormatSymbols = symbols
    }

    fun format(number: Double): String {
        return formatter.format(number)
    }

    fun parse(number: String): String {
        return number.replace(",", "")
    }
}
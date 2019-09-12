package com.scan.currencyconverter.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class Formatter {
    private val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    private val symbols = formatter.decimalFormatSymbols
    init {
        formatter.decimalFormatSymbols = symbols
    }

    fun format(number: Double, pattern: String): String {
        formatter.applyPattern(pattern)
        return formatter.format(number)
    }

    fun parse(number: String): String {
        return number.replace(",", "")
    }
}
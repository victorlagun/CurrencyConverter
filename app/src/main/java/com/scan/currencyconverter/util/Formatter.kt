package com.scan.currencyconverter.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class Formatter {
    private val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
    private val symbols = formatter.decimalFormatSymbols
    private val pattern = ",##0.00"

    init {
        formatter.applyPattern(pattern)
        formatter.decimalFormatSymbols = symbols
    }

    fun format(number: Double): String {
        return formatter.format(number)
    }

    fun parse(number: String): Double {
        return formatter.parse(number) as Double
    }
}
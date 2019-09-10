package com.scan.currencyconverter.util

import com.scan.currencyconverter.model.CurrencyOfficialRate

class Converter(private val rates: List<CurrencyOfficialRate>, fromCurrency: String) {
    private val fromRate = getCurrencyRate(fromCurrency)

    fun convert(amount: Double, currencyAbbreviation: String): Double {
        return amount * getConversionRate(fromRate, getCurrencyRate(currencyAbbreviation))
    }

    private fun getConversionRate(fromRate: Double, toRate: Double): Double {
        return if (fromRate == toRate) fromRate
        else fromRate / toRate
    }

    private fun getCurrencyRate(currencyAbbreviation: String): Double {
        val currencyOfficialRate =
            rates.filter { rate -> rate.Cur_Abbreviation == currencyAbbreviation }[0]
        return currencyOfficialRate.Cur_OfficialRate / currencyOfficialRate.Cur_Scale
    }
}
package com.scan.currencyconverter.util

import com.scan.currencyconverter.model.CurrencyOfficialRate

class Converter {

    fun convert(
        amount: Double,
        to: String,
        from: String,
        rates: List<CurrencyOfficialRate>
    ): Double {
        return amount * getConversionRate(getCurrencyRate(from, rates), getCurrencyRate(to, rates))
    }

    private fun getConversionRate(fromRate: Double, toRate: Double): Double {
        return if (fromRate == toRate) fromRate
        else fromRate / toRate
    }

    private fun getCurrencyRate(
        currencyAbbreviation: String,
        rates: List<CurrencyOfficialRate>
    ): Double {
        val currencyOfficialRate =
            rates.filter { rate -> rate.Cur_Abbreviation == currencyAbbreviation }[0]
        return currencyOfficialRate.Cur_OfficialRate / currencyOfficialRate.Cur_Scale
    }
}
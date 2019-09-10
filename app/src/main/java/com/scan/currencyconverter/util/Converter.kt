package com.scan.currencyconverter.util

import com.scan.currencyconverter.model.CurrencyOfficialRate

class Converter {

    fun convert(amount: Int, USDRate: Double): Double {
        return amount * USDRate
    }

    fun getUSDRate(currencyRateToBYN: Double, usdRateToBYN: Double): Double {
        return usdRateToBYN / currencyRateToBYN
    }

    fun getCurrencyRateToBYN(
        currencyAbbreviation: String,
        rates: List<CurrencyOfficialRate>
    ): Double {
        return rates.filter { rate -> rate.Cur_Abbreviation == currencyAbbreviation }[0].Cur_OfficialRate
    }
}
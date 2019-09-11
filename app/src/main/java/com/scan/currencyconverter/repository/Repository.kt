package com.scan.currencyconverter.repository

import com.scan.currencyconverter.model.CurrencyOfficialRate
import com.scan.currencyconverter.repository.remote.Remote
import io.reactivex.Observable

class Repository(private val remote: Remote) {

    fun rates(periodicity: Int): Observable<List<CurrencyOfficialRate>> {
        return remote.rates(periodicity)
    }
}
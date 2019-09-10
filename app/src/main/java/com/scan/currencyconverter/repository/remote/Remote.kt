package com.scan.currencyconverter.repository.remote

import com.scan.currencyconverter.model.CurrencyOfficialRate
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Remote {

    @GET("rates")
    fun rates(@Query("periodicity") periodicity: Int): Observable<List<CurrencyOfficialRate>>

    companion object Factory {
        fun create(): Remote {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.nbrb.by/api/exrates/")
                .build()

            return retrofit.create(Remote::class.java)
        }
    }

}
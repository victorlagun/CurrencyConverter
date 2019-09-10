package com.scan.currencyconverter.presenter.contract

interface CurrencyConverterContract {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun init()
    }
}
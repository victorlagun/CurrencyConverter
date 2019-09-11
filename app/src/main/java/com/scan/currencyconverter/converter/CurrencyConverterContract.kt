package com.scan.currencyconverter.converter

interface CurrencyConverterContract {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface Presenter {
        fun loadRates()
        fun formatInput()
        fun formatOutput()
    }
}
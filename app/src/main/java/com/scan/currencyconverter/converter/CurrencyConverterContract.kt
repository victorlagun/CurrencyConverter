package com.scan.currencyconverter.converter

interface CurrencyConverterContract {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
        fun getInput()
        fun setInput(input: String)
        fun setEur(eur: String)
        fun setRub(rub: String)
        fun setByn(byn: String)
        fun getCursorPosition(): Int
        fun setCursorPosition(position: Int)
        fun addTextWatcher()
        fun removeTextWatcher()
        fun clear()
    }

    interface Presenter {
        fun setView(view: View)
        fun handleInput(input: String)
    }
}
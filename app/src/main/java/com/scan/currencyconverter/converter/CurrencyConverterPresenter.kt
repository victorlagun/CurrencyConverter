package com.scan.currencyconverter.converter

import com.scan.currencyconverter.converter.CurrencyConverterContract.Presenter
import com.scan.currencyconverter.model.*
import com.scan.currencyconverter.repository.Repository
import com.scan.currencyconverter.repository.remote.Remote
import com.scan.currencyconverter.util.Converter
import com.scan.currencyconverter.util.Formatter
import com.scan.currencyconverter.util.removeLastChar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CurrencyConverterPresenter : Presenter {

    private val repository = Repository(Remote.create())
    private val converter = Converter()
    private val formatter = Formatter()

    private lateinit var view: CurrencyConverterContract.View
    private lateinit var rates: List<CurrencyOfficialRate>

    override fun setView(view: CurrencyConverterContract.View) {
        this.view = view
        loadRates()
    }

    override fun handleInput(input: String) {
        if (input.isNotEmpty()) {
            if (input.contains(".") && input.substringAfter(".", "").isEmpty()) {
                view.setEur(formatOutput(getParsedInput(input), EUR))
                view.setRub(formatOutput(getParsedInput(input), RUB))
                view.setByn(formatOutput(getParsedInput(input), BYN))
            } else {
                formatInput(input)
                view.setEur(formatOutput(getParsedInput(input), EUR))
                view.setRub(formatOutput(getParsedInput(input), RUB))
                view.setByn(formatOutput(getParsedInput(input), BYN))
            }
        } else {
            view.clear()
        }
    }

    private fun loadRates() {
        view.showProgressBar(true)
        repository.rates(0)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doAfterTerminate {
                view.showProgressBar(false)
            }
            .subscribe({ result ->
                rates = result
                view.setInput("1")
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun formatInput(input: String) {
        val parsedInput = getParsedInput(input)
        val formattedInput = formatter.format(parsedInput.toDouble(), PATTERN_INPUT)
        val newCursorPosition =
            getCursorPosition(view.getCursorPosition(), input, formattedInput)
        view.removeTextWatcher()
        view.setInput(formatter.format(parsedInput.toDouble(), PATTERN_INPUT))
        view.setCursorPosition(newCursorPosition)
        view.addTextWatcher()
    }

    private fun getParsedInput(input: String): String {
        return formatDecimalPart(formatter.parse(input))
    }

    private fun formatDecimalPart(input: String): String {
        if (input.contains(".") && input.substringAfter(".", "").length > 2)
            return removeLastChar(input)
        return input
    }

    private fun getCursorPosition(previousPos: Int, textBefore: String, textAfter: String): Int =
        if (previousPos + textAfter.length - textBefore.length >= 0)
            previousPos + textAfter.length - textBefore.length
        else 0

    private fun formatOutput(input: String, currency: String): String {
        return formatter.format(
            converter.convert(input.toDouble(), currency, USD, rates), PATTERN_OUTPUT
        )
    }
}
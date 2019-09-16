package com.scan.currencyconverter.converter

import com.scan.currencyconverter.converter.CurrencyConverterContract.Presenter
import com.scan.currencyconverter.model.*
import com.scan.currencyconverter.repository.Repository
import com.scan.currencyconverter.repository.remote.Remote
import com.scan.currencyconverter.util.Converter
import com.scan.currencyconverter.util.Formatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class CurrencyConverterPresenter : Presenter {

    private val repository = Repository(Remote.create())
    private val converter = Converter()
    private val formatter = Formatter()

    private lateinit var view: WeakReference<CurrencyConverterContract.View>
    private lateinit var rates: List<CurrencyOfficialRate>

    override fun setView(view: CurrencyConverterContract.View) {
        this.view = WeakReference(view)
        loadRates()
    }

    override fun handleInput(input: String) {
        view.get()?.let {
            if (input.isNotEmpty()) {
                val input = formatDecimalPart(input)
                if (input.contains(".") && (input.substringAfter(".", "").isEmpty()
                            || input.substringAfter(".", "").length == 1 && input[input.length - 1] == '0'
                            || input.substringAfter(".", "").length == 2 && input[input.length - 1] == '0')) {
                    if (input.length > 1) {
                        showInput(formatDecimalPart(it.getInput()))
                        showOutput(input)
                    }
                } else {
                    formatInput(input)
                    showOutput(input)
                }
            } else {
                it.clear()
            }
        }
    }

    private fun loadRates() {
        view.get()?.let {
            it.showProgressBar(true)
            repository.rates(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doAfterTerminate {
                    it.showProgressBar(false)
                }
                .subscribe({ result ->
                    rates = result
                    it.setInput("1")
                }, { error ->
                    error.printStackTrace()
                })
        }
    }

    private fun formatInput(input: String) {
        view.get()?.let {
            val parsedInput = getParsedInput(input)
            showInput(formatter.format(parsedInput.toDouble(), PATTERN_INPUT))
        }
    }

    private fun showInput(input: String) {
        view.get()?.let {
            it.removeTextWatcher()
            it.setInput(input)
            it.addTextWatcher()
        }
    }

    private fun showOutput(input: String) {
        view.get()?.let {
            it.setEur(formatOutput(getParsedInput(input), EUR))
            it.setRub(formatOutput(getParsedInput(input), RUB))
            it.setByn(formatOutput(getParsedInput(input), BYN))
        }
    }

    private fun getParsedInput(input: String): String {
        return formatDecimalPart(formatter.parse(input))
    }

    private fun formatDecimalPart(input: String): String {
        if (input.contains(".") && input.substringAfter(".", "").length > 2)
            return input.substring(0, input.substringBefore(".", "").length + 3)
        return input
    }

    private fun formatOutput(input: String, currency: String): String {
        return formatter.format(
            converter.convert(input.toDouble(), currency, USD, rates), PATTERN_OUTPUT
        )
    }
}
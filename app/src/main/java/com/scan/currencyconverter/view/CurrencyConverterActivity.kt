package com.scan.currencyconverter.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.scan.currencyconverter.R
import com.scan.currencyconverter.model.CurrencyOfficialRate
import com.scan.currencyconverter.repository.Repository
import com.scan.currencyconverter.repository.remote.Remote
import com.scan.currencyconverter.util.Converter
import com.scan.currencyconverter.util.Formatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_currency_converter.*


class CurrencyConverterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)
        val repository = Repository(Remote.create())
        repository.rates(0)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                init(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    fun init(rates: List<CurrencyOfficialRate>) {
        val converter = Converter(rates, resources.getString(R.string.usd))
        val formatter = Formatter()
        usdEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    eurEditText.setText(formatter.format(converter.convert(s.toString().toDouble(), resources.getString(R.string.eur))))
                    rubEditText.setText(formatter.format(converter.convert(s.toString().toDouble(), resources.getString(R.string.rub))))
                    bynEditText.setText(formatter.format(converter.convert(s.toString().toDouble(), resources.getString(R.string.usd))))
                }
            }
        })
        usdEditText.setText("1")

    }
}

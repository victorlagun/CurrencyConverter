package com.scan.currencyconverter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scan.currencyconverter.R
import com.scan.currencyconverter.model.CurrencyOfficialRate
import com.scan.currencyconverter.repository.Repository
import com.scan.currencyconverter.repository.remote.Remote
import com.scan.currencyconverter.util.Converter
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
        val converter = Converter()
        val usdToByn = converter.getCurrencyRateToBYN(resources.getString(R.string.usd), rates)
        val eurToByn = converter.getCurrencyRateToBYN(resources.getString(R.string.eur), rates)
        val rubToByn = converter.getCurrencyRateToBYN(resources.getString(R.string.rub), rates)
        val usdToEur = converter.getUSDRate(eurToByn, usdToByn)
        val usdToRub = converter.getUSDRate(rubToByn, usdToByn) * 100
        usdEditText.setText("1")
        eurEditText.setText(converter.convert(1, usdToEur).toString())
        rubEditText.setText(converter.convert(1, usdToRub).toString())
        bynEditText.setText(converter.convert(1, usdToByn).toString())

    }
}

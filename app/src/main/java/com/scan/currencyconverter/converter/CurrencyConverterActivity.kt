package com.scan.currencyconverter.converter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.scan.currencyconverter.R
import com.scan.currencyconverter.model.CurrencyOfficialRate
import com.scan.currencyconverter.repository.Repository
import com.scan.currencyconverter.repository.remote.Remote
import com.scan.currencyconverter.util.Converter
import com.scan.currencyconverter.util.Formatter
import com.scan.currencyconverter.util.setSpan
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_currency_converter.*

class CurrencyConverterActivity : AppCompatActivity() {

    private val patternOutput = "###,##0.00"
    private val patternInput = "###,###.##"

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

    fun removeLastChar(s: String?): String? {
        return if (s == null || s.length == 0)
            null
        else
            s.substring(0, s.length - 1)
    }

    fun init(rates: List<CurrencyOfficialRate>) {
        val converter = Converter(rates, resources.getString(R.string.usd))
        val formatterOutput = Formatter(patternOutput)
        val formatterInput = Formatter(patternInput)
        eurTextView.movementMethod = ScrollingMovementMethod()
        rubTextView.movementMethod = ScrollingMovementMethod()
        bynTextView.movementMethod = ScrollingMovementMethod()


        usdEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val inputBefore = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    if (s.contains(".") && s.toString().substringAfter(".", "").isEmpty()) {
                        return
                    } else {
                        var value = s.toString()
                        if (value.contains(".") && value.substringAfter(".", "").length > 2)
                            value = removeLastChar(value).toString()
                        val usd = formatterInput.parse(value)
                        usdEditText.removeTextChangedListener(this)
                        val cursor = usdEditText.selectionStart
                        usdEditText.setText(setSpan(formatterInput.format(usd.toDouble()), 1.33f, Color.BLACK))
                        if (cursor<=usd.length)
                        usdEditText.setSelection(cursor)
                        else
                            usdEditText.setSelection(usd.length)
                        usdEditText.addTextChangedListener(this)

                        val eur = formatterOutput.format(
                            converter.convert(usd.toDouble(), resources.getString(R.string.eur))
                        )
                        eurTextView.text = setSpan(eur, 1.33f, Color.BLACK)

                        val rub = formatterOutput.format(
                            converter.convert(usd.toDouble(), resources.getString(R.string.rub))
                        )
                        rubTextView.text = setSpan(rub, 1.33f, Color.BLACK)

                        val byn = formatterOutput.format(
                            converter.convert(usd.toDouble(), resources.getString(R.string.usd))
                        )
                        bynTextView.text = setSpan(byn, 1.33f, Color.BLACK)
                    }
                }
            }
        })
    }
}

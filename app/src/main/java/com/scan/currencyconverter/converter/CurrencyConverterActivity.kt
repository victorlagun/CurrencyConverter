package com.scan.currencyconverter.converter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.scan.currencyconverter.R
import com.scan.currencyconverter.model.TEXT_HIGH
import com.scan.currencyconverter.util.setSpan
import kotlinx.android.synthetic.main.activity_currency_converter.*

class CurrencyConverterActivity : AppCompatActivity(), CurrencyConverterContract.View {


    lateinit var presenter: CurrencyConverterContract.Presenter
    val textWatcher = getTextWatcher(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)
        init()
    }

    private fun init() {
        presenter = CurrencyConverterPresenter()
        presenter.setView(this)

        eurTextView.movementMethod = ScrollingMovementMethod()
        rubTextView.movementMethod = ScrollingMovementMethod()
        bynTextView.movementMethod = ScrollingMovementMethod()

        addTextWatcher()
    }

//    fun init(rates: List<CurrencyOfficialRate>) {
//        val converter = Converter(rates, resources.getString(R.string.usd))
//        val formatterOutput = Formatter(patternOutput)
//        val formatterInput = Formatter(patternInput)
//        eurTextView.movementMethod = ScrollingMovementMethod()
//        rubTextView.movementMethod = ScrollingMovementMethod()
//        bynTextView.movementMethod = ScrollingMovementMethod()
//
//
//        usdEditText.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                val inputBefore = s.toString()
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.isNotEmpty()) {
//                    if (s.contains(".") && s.toString().substringAfter(".", "").isEmpty()) {
//                        return
//                    } else {
//                        var value = s.toString()
//                        if (value.contains(".") && value.substringAfter(".", "").length > 2)
//                            value = removeLastChar(value).toString()
//                        val usd = formatterInput.parse(value)
//                        usdEditText.removeTextChangedListener(this)
//                        val cursor = usdEditText.selectionStart
//                        usdEditText.setText(
//                            setSpan(
//                                formatterInput.format(usd.toDouble()),
//                                1.33f,
//                                Color.BLACK
//                            )
//                        )
//                        if (cursor <= usd.length)
//                            usdEditText.setSelection(cursor)
//                        else
//                            usdEditText.setSelection(usd.length)
//                        usdEditText.addTextChangedListener(this)
//
//                        val eur = formatterOutput.format(
//                            converter.convert(usd.toDouble(), resources.getString(R.string.eur))
//                        )
//                        eurTextView.text = setSpan(eur, 1.33f, Color.BLACK)
//
//                        val rub = formatterOutput.format(
//                            converter.convert(usd.toDouble(), resources.getString(R.string.rub))
//                        )
//                        rubTextView.text = setSpan(rub, 1.33f, Color.BLACK)
//
//                        val byn = formatterOutput.format(
//                            converter.convert(usd.toDouble(), resources.getString(R.string.usd))
//                        )
//                        bynTextView.text = setSpan(byn, 1.33f, Color.BLACK)
//                    }
//                }
//            }
//        })
//    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {
    }

    override fun getInput() {
    }

    override fun setInput(input: String) {
        usdEditText.setText(
            setSpan(input, TEXT_HIGH, Color.BLACK)
        )
    }

    override fun setEur(eur: String) {
        eurTextView.text = setSpan(eur, TEXT_HIGH, Color.BLACK)
    }

    override fun setRub(rub: String) {
        rubTextView.text = setSpan(rub, TEXT_HIGH, Color.BLACK)
    }

    override fun setByn(byn: String) {
        bynTextView.text = setSpan(byn, TEXT_HIGH, Color.BLACK)
    }

    override fun getCursorPosition(): Int {
        return usdEditText.selectionStart
    }

    override fun setCursorPosition(position: Int) {
        usdEditText.setSelection(position)
    }

    override fun addTextWatcher() {
        usdEditText.addTextChangedListener(textWatcher)
    }

    override fun removeTextWatcher() {
        usdEditText.removeTextChangedListener(textWatcher)
    }

    override fun clear() {
        eurTextView.text = ""
        rubTextView.text = ""
        bynTextView.text = ""
    }

    companion object {
        private fun getTextWatcher(currencyConverterActivity: CurrencyConverterActivity): TextWatcher {
            return object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    currencyConverterActivity.presenter.handleInput(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
        }
    }
}

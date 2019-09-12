package com.scan.currencyconverter.converter

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.scan.currencyconverter.R
import com.scan.currencyconverter.model.TEXT_HIGH
import com.scan.currencyconverter.util.isNetworkAvailable
import com.scan.currencyconverter.util.setSpan
import kotlinx.android.synthetic.main.activity_currency_converter.*

class CurrencyConverterActivity : AppCompatActivity(), CurrencyConverterContract.View {

    lateinit var presenter: CurrencyConverterContract.Presenter
    private val textWatcher = getTextWatcher(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)
        init()
    }

    private fun init() {
        if (!isNetworkAvailable(this)) {
            network_state.visibility = View.VISIBLE
            return
        }

        presenter = CurrencyConverterPresenter()
        presenter.setView(this)

        eurTextView.movementMethod = ScrollingMovementMethod()
        rubTextView.movementMethod = ScrollingMovementMethod()
        bynTextView.movementMethod = ScrollingMovementMethod()

        addTextWatcher()
    }

    override fun showProgressBar(boolean: Boolean) {
        if (boolean)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
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

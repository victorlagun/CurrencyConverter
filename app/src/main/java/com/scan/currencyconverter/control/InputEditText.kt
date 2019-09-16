package com.scan.currencyconverter.control

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText



class InputEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputEditText(context, attrs, defStyleAttr) {

    public override fun onSelectionChanged(start: Int, end: Int) {
        val text = text
        if (text != null) {
            if (start != text.length || end != text.length) {
                setSelection(text.length, text.length)
                return
            }
        }
        super.onSelectionChanged(start, end)
    }

}
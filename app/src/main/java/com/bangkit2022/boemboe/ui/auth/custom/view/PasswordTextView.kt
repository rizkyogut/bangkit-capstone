package com.bangkit2022.boemboe.ui.auth.custom.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.bangkit2022.boemboe.R

class PasswordTextView : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty())
                    error = if (passwordValidation(s.toString())) null else context.getString(
                        R.string.passwordValidation_alert)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun passwordValidation(pass: String): Boolean {
        return pass.length >= 6
    }
}
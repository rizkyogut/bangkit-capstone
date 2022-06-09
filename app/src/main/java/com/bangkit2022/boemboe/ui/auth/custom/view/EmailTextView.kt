package com.bangkit2022.boemboe.ui.auth.custom.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.bangkit2022.boemboe.R

class EmailTextView : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().isNotEmpty()){
                    error = if(emailValidation(p0.toString())) null else context.getString(R.string.emailValidation_alert)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            private fun emailValidation(s:String):Boolean{
                return android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()
            }
        })
    }
}
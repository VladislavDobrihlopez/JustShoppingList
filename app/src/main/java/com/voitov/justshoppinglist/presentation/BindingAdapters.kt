package com.voitov.justshoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.voitov.justshoppinglist.R

@BindingAdapter("floatAsString")
fun bindFloatAsString(textInputEditText: TextInputEditText, count: Float) {
    textInputEditText.setText(count.toString())
}

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isValueInvalid: Boolean) {
    textInputLayout.error = if (isValueInvalid) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isValueInvalid: Boolean) {
    textInputLayout.error = if (isValueInvalid) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
}
package com.ahuynh.muzimusicapp.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.ahuynh.muzimusicapp.R

class CustomPasswordEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var isPasswordVisible: Boolean = false
    private val eyeOpenIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_visibility)!!
    private val eyeClosedIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_visibility_off)!!

    init {
        setup()
    }

    private fun setup() {
        updateIcon()

        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
                if (event.rawX >= (right - compoundDrawables[drawableEnd].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            transformationMethod = PasswordTransformationMethod.getInstance()
            updateIcon()
        } else {
            transformationMethod = HideReturnsTransformationMethod.getInstance()
            updateIcon()
        }

        setSelection(text?.length ?: 0)
        isPasswordVisible = !isPasswordVisible
    }

    private fun updateIcon() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, if (isPasswordVisible) eyeOpenIcon else eyeClosedIcon, null)
    }
}
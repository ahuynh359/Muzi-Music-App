package com.ahuynh.muzimusicapp.data.model

import androidx.annotation.DrawableRes

data class SliderItem (
    @DrawableRes val image: Int,
    val title : String,
    val description: String

)
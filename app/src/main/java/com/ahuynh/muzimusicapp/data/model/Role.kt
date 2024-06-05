package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Role(
    val id: Long,
    val name: String,
) : Parcelable
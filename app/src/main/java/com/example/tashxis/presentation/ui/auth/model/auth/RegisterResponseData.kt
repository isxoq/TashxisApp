package com.example.tashxis.presentation.ui.auth.model.auth


import com.google.gson.annotations.SerializedName

data class RegisterResponseData(
    @SerializedName("expired_in")
    val expiredIn: Int // 120
)
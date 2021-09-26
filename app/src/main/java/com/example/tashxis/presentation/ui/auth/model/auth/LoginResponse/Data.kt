package com.example.tashxis.presentation.ui.auth.model.auth.LoginResponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("expired_in")
    val expiredIn: Int // 30
)
package com.example.tashxis.presentation.ui.auth.model.auth.login_verify


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginVerifyResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean // true
)
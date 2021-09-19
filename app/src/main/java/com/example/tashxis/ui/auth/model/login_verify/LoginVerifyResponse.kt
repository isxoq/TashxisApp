package com.example.tashxis.ui.auth.model.login_verify


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginVerifyResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean // true
)
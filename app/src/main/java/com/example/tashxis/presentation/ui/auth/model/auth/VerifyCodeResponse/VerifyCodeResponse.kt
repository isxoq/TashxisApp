package com.example.tashxis.presentation.ui.auth.model.auth.VerifyCodeResponse

import com.google.gson.annotations.SerializedName

data class VerifyCodeResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean // true
)
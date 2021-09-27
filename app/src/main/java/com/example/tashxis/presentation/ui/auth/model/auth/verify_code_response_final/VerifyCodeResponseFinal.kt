package com.example.tashxis.presentation.ui.auth.model.auth.verify_code_response_final


import com.google.gson.annotations.SerializedName

data class VerifyCodeResponseFinal(
    @SerializedName("data")
    val data: VerifyCodeData?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean // true
)
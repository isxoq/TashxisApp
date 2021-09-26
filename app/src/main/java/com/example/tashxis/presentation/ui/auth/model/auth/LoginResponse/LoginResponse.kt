package com.example.tashxis.presentation.ui.auth.model.auth.LoginResponse


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String, // code sent
    @SerializedName("success")
    val success: Boolean // true
)
package com.example.tashxis.presentation.ui.auth.model.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val data: Any?,
    @SerializedName("message")
    val message: String, // Phone already registered
    @SerializedName("success")
    val success: Boolean // false
)
package com.example.tashxis.presentation.ui.auth.model.auth.ProfileInfoResponse


import com.google.gson.annotations.SerializedName

data class AddProfileInfoResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean // true
)
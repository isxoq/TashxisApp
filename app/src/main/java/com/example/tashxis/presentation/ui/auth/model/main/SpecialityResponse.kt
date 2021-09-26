package com.example.tashxis.presentation.ui.auth.model.main


import com.google.gson.annotations.SerializedName

data class SpecialityResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean // true
)
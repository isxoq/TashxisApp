package com.example.tashxis.presentation.ui.auth.model.auth.RegionResponse


import com.google.gson.annotations.SerializedName

data class RegionResponse(
    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean // true
)
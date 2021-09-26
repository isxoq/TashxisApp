package com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse


import com.google.gson.annotations.SerializedName

data class DistrictResponse(
    @SerializedName("data")
    val data: Any?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean // true
)
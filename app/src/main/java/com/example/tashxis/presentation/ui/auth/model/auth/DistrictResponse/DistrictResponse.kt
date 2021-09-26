package com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse


import com.google.gson.annotations.SerializedName

data class DistrictResponse(
    @SerializedName("data")
    val data: List<DistrictData>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean // true
)
package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.speciality_response


import com.google.gson.annotations.SerializedName

data class SpecialityResponse(
    @SerializedName("data")
    val data: List<SpecialityData>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean // true
)
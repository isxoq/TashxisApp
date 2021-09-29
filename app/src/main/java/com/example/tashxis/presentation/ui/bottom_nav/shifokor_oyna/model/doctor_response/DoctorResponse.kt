package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response


import com.google.gson.annotations.SerializedName

data class DoctorResponse(
    @SerializedName("data")
    val data: List<DoctorResponseData>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean // true
)
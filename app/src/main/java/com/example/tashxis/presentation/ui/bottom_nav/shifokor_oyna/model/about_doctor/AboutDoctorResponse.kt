package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor


import com.google.gson.annotations.SerializedName

data class AboutDoctorResponse(
    @SerializedName("data")
    val data: AboutDoctorResponseData?,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean // true
)
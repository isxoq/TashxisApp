package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response


import com.google.gson.annotations.SerializedName

data class Speciality(
    @SerializedName("description")
    val description: String, // Yurak kasalliklari
    @SerializedName("doctorsCount")
    val doctorsCount: String, // 4
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("logoUrl")
    val logoUrl: String, // /uploads/images/speciality/1/preview-6153de15bd10c.png
    @SerializedName("name")
    val name: String, // Kardiolog
    @SerializedName("status")
    val status: Int // 1
)
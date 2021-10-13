package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response


import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("id")
    val id: Int, // 30
    @SerializedName("name")
    val name: String, // Andijon shahri
    @SerializedName("region_id")
    val regionId: Int // 2
)
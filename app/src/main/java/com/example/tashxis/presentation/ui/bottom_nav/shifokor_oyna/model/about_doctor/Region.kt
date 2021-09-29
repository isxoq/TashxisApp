package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor


import com.google.gson.annotations.SerializedName

data class Region(
    @SerializedName("id")
    val id: Int, // 127
    @SerializedName("name")
    val name: String, // Denov tumani
    @SerializedName("region_id")
    val regionId: Int // 9
)
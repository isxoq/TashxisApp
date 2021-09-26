package com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int, // 15
    @SerializedName("name")
    val name: String, // Amudaryo tumani
    @SerializedName("region_id")
    val regionId: Int // 1
)
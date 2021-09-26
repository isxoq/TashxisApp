package com.example.tashxis.presentation.ui.auth.model.auth.RegionResponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("name")
    val name: String // Qoraqalpog‘iston Respublikasi
)
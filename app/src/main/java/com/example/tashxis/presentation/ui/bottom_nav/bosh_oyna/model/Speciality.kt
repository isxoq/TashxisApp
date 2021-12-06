package com.example.tashxis.presentation.ui.bottom_nav.bosh_oyna.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Speciality(
    @SerializedName("description")
    val description: String, // Tish shifokorlari
    @SerializedName("doctorsCount")
    val doctorsCount: String, // 5
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("logoUrl")
    val logoUrl: String, // /uploads/images/speciality/2/preview-6153de2174bfa.png
    @SerializedName("name")
    val name: String, // Stomatolog
    @SerializedName("status")
    val status: Int // 1
)
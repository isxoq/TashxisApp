package com.example.tashxis.presentation.ui.bottom_nav.bosh_oyna.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetNearDoctorsRequest(
    @SerializedName("distance")
    val distance: Int, // 1000
    @SerializedName("lat")
    val lat: Int, // 40
    @SerializedName("long")
    val long: Int // 70
)
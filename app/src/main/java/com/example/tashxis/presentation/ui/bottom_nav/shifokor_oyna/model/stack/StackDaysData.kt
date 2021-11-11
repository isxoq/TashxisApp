package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack


import com.google.gson.annotations.SerializedName

data class StackDaysData(
    @SerializedName("date")
    val date: String, // 06.11.2021
    @SerializedName("weekday")
    val weekday: String, // Shanba
    var selected: Boolean = false
)
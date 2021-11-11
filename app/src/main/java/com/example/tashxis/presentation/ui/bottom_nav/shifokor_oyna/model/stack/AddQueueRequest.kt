package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack


import com.google.gson.annotations.SerializedName

data class AddQueueRequest(
    @SerializedName("doctor_id")
    val doctorId: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("is_payed")
    val isPayed: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String
)
package com.example.tashxis.ui.auth.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OtpResponse(
    @SerializedName("data")
    val data: List<Any>,
    @SerializedName("message")
    val message: String, // Foydalanuvchi topilmadi
    @SerializedName("success")
    val success: Boolean // false
)
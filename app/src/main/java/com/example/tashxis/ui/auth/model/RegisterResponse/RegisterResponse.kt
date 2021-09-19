package com.example.tashxis.ui.auth.model.RegisterResponse


import androidx.annotation.Keep
import com.example.tashxis.ui.auth.model.test2.Data
import com.google.gson.annotations.SerializedName

@Keep
data class RegisterResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String, // code sent
    @SerializedName("success")
    val success: Boolean // true
)
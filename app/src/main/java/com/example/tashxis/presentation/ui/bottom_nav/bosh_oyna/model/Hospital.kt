package com.example.tashxis.presentation.ui.bottom_nav.bosh_oyna.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Hospital(
    @SerializedName("address")
    val address: String, // Andijon viloyati A.Yo'ldoshev ko'chasi 41a uy
    @SerializedName("deleted")
    val deleted: Any?, // null
    @SerializedName("email")
    val email: String,
    @SerializedName("fax")
    val fax: String,
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("image")
    val image: String, // 6152e969d21e0.jpg
    @SerializedName("imageUrl")
    val imageUrl: String, // /uploads/images/hospital/3/preview-6152e969d21e0.jpg
    @SerializedName("lat")
    val lat: String, // 40.8153684957179
    @SerializedName("logo")
    val logo: String, // 6152e853bd8b2.png
    @SerializedName("logoUrl")
    val logoUrl: String, // /uploads/images/hospital/3/preview-6152e853bd8b2.png
    @SerializedName("long")
    val long: String, // 72.28378066236714
    @SerializedName("name")
    val name: String, // "Diyor" klinikasi
    @SerializedName("password")
    val password: Any?, // null
    @SerializedName("phone")
    val phone: String, // +998742240303
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("username")
    val username: Any? // null
)
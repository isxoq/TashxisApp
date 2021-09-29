package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Hospital(
    @SerializedName("address")
    val address: String, // Qayirma MFY Boyto'pi ko'chasi 9-uy
    @SerializedName("deleted")
    val deleted: Any?, // null
    @SerializedName("email")
    val email: String, // isxoqjon_7710@mail.ru
    @SerializedName("fax")
    val fax: String, // +998993657710
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("image")
    val image: String, // 613070fb3ae81.jpg
    @SerializedName("imageUrl")
    val imageUrl: String, // /uploads/images/hospital/1/preview-613070fb3ae81.jpg
    @SerializedName("lat")
    val lat: String, // 40.9186904
    @SerializedName("logo")
    val logo: String, // 6151282b69f68.png
    @SerializedName("logoUrl")
    val logoUrl: String, // /uploads/images/hospital/1/preview-6151282b69f68.png
    @SerializedName("long")
    val long: String, // 68.5784621
    @SerializedName("name")
    val name: String, // Andijon ko'p tarmoqli tibbiyot markazi	
    @SerializedName("password")
    val password: String?, // null
    @SerializedName("phone")
    val phone: String, // +998993657710
    @SerializedName("status")
    val status: Int, // 1
    @SerializedName("username")
    val username: String? // null
)
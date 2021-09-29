package com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.speciality_response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecialityData(
    @SerializedName("description")
    val description: String?, // null
    @SerializedName("doctorsCount")
    val doctorsCount: String?, // 0
    @SerializedName("id")
    val id: Int?, // 1
    @SerializedName("logoUrl")
    val logoUrl: String?, // null
    @SerializedName("name")
    val name: String?, // Kardilog
    @SerializedName("status")
    val status: Int? // 1
):Parcelable
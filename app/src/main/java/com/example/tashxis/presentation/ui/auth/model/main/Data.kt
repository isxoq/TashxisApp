package com.example.tashxis.presentation.ui.auth.model.main


import com.google.gson.annotations.SerializedName

data class Data(
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
)
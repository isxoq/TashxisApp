package com.example.tashxis.ui.auth.model

import com.google.gson.annotations.SerializedName

data class EditUserModel(
    @field:SerializedName("genter")
    val gender: String? = null
)

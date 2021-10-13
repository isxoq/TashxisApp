package com.example.tashxis.presentation.ui.auth.model.auth


import com.google.gson.annotations.SerializedName

data class VerifyCodeData(
    @SerializedName("auth_key")
    val authKey: String, // _JB8QNrOO35iTajHY0ZDY8p0iyRg5ipg
    @SerializedName("created_at")
    val createdAt: Int?, // 1632631645
    @SerializedName("email")
    val email: String?, // 998937309601
    @SerializedName("id")
    val id: Int?, // 42
    @SerializedName("password_hash")
    val passwordHash: String?, // $2y$13$1D.XJXH5V5kVRB/jHIDzxuIewuDWQueL1CycAdxIplNJwE6jtTxh2
    @SerializedName("phone")
    val phone: String?, // 998937309601
    @SerializedName("status")
    val status: Int?, // 10
    @SerializedName("type_id")
    val typeId: Int?, // 2
    @SerializedName("updated_at")
    val updatedAt: Int?, // 1632631645
    @SerializedName("username")
    val username: String? // 998937309601
)
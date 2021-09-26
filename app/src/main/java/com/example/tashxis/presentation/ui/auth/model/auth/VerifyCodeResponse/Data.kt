package com.example.tashxis.presentation.ui.auth.model.auth.VerifyCodeResponse

data class Data(
    val auth_key: String,
    val created_at: Int,
    val email: String,
    val id: Int,
    val password_hash: String,
    val phone: String,
    val status: Int,
    val type_id: Int,
    val updated_at: Int,
    val username: String
)
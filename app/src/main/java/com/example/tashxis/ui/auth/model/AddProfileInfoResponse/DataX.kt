package com.example.tashxis.ui.auth.model.AddProfileInfoResponse

data class DataX(
    val auth_key: String,
    val birth_date: String,
    val created_at: Int,
    val email: String,
    val father_name: String,
    val first_name: String,
    val gender: String,
    val hospital_id: Any,
    val id: Int,
    val image: Any,
    val last_name: String,
    val password_hash: String,
    val password_reset_token: Any,
    val phone: String,
    val province_id: String,
    val region_id: String,
    val status: Int,
    val type_id: Int,
    val updated_at: Int,
    val username: String
)
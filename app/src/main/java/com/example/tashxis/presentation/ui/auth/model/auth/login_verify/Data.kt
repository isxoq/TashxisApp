package com.example.tashxis.presentation.ui.auth.model.auth.login_verify


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("auth_key")
    val authKey: String?, // oBA8f0mLEf3zaPjE_1630749883
    @SerializedName("birth_date")
    val birthDate: String?, // 1998-08-29
    @SerializedName("created_at")
    val createdAt: Int?, // 1630726690
    @SerializedName("email")
    val email: String?, // 998937309600
    @SerializedName("father_name")
    val fatherName: String?, // G'ofurali o'g'li
    @SerializedName("first_name")
    val firstName: String?, // Abduqahhor
    @SerializedName("gender")
    val gender: Int?, // 1
    @SerializedName("hospital_id")
    val hospitalId: String?, // null
    @SerializedName("id")
    val id: Int?, // 6
    @SerializedName("image")
    val image: String?, // null
    @SerializedName("last_name")
    val lastName: String?, // Otajonov
    @SerializedName("password_hash")
    val passwordHash: String?, // $2y$13$dQv.ryH19HJkR9q9rwH9g.zU9OqcWSHG5XXQ5HLsueXpcrOlLk1Ju
    @SerializedName("password_reset_token")
    val passwordResetToken: String?, // null
    @SerializedName("phone")
    val phone: String?, // 998937309600
    @SerializedName("province_id")
    val provinceId: Int?, // 12
    @SerializedName("region_id")
    val regionId: Int?, // 12
    @SerializedName("status")
    val status: Int?, // 10
    @SerializedName("type_id")
    val typeId: Int?, // 2
    @SerializedName("updated_at")
    val updatedAt: Int?, // 1630749883
    @SerializedName("username")
    val username: String? // 998937309600
)
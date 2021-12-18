package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor


import com.google.gson.annotations.SerializedName

data class Speciality(
    @SerializedName("description")
    val description: String, // Yurak kasalliklari
    @SerializedName("doctorsCount")
    val doctorsCount: String, // 1
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("logoUrl")
    val logoUrl: String, // /uploads/images/speciality/1/preview-6151288cd0f58.jpg
    @SerializedName("name")
    val name: String, // Kardiolog
    @SerializedName("status")
    val status: Int // 1
)
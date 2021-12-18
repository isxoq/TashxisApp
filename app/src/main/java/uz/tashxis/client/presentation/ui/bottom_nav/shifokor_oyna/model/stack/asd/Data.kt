package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.asd


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("created_at")
    val createdAt: Int, // 1639537559
    @SerializedName("date")
    val date: String, // 2021-11-09
    @SerializedName("doctor_address")
    val doctorAddress: String, // Andijon viloyati A.Yo'ldoshev ko'chasi 41a uy
    @SerializedName("doctor_first_name")
    val doctorFirstName: String, // Nailya
    @SerializedName("doctor_image")
    val doctorImage: String, // /uploads/images/doctor/70/preview-61512b3737917.png
    @SerializedName("doctor_last_name")
    val doctorLastName: String, // O'zbekova
    @SerializedName("doctor_speciality")
    val doctorSpeciality: String, // Kardiolog
    @SerializedName("father_name")
    val fatherName: String, // G'ofurali o'g'li
    @SerializedName("first_name")
    val firstName: String, // Abduqahhor
    @SerializedName("id")
    val id: Int, // 254
    @SerializedName("last_name")
    val lastName: String, // Otajonov
    @SerializedName("phone")
    val phone: String, // 998937309600
    @SerializedName("queue_number")
    val queueNumber: Int, // 8
    @SerializedName("states")
    val states: List<State>,
    @SerializedName("time")
    val time: String // 14:00
)
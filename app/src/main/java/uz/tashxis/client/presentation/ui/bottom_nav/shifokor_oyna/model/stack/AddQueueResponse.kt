package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack

import com.google.gson.annotations.SerializedName


data class AddQueueResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("queue_number")
    val queueNumber: String,
    @SerializedName("doctor_image")
    val doctorImage: String?,
    @SerializedName("doctor_first_name")
    val doctorFirstName: String?,
    @SerializedName("doctor_last_name")
    val doctorLastName: String?,
    @SerializedName("doctor_speciality")
    val doctorSpeciality: String?,
    @SerializedName("doctor_address")
    val doctorAddress: String?,
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String
)
package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.asd


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class State(
    @SerializedName("datetime")
    val datetime: String, // 15.12.2021 06:05:59
    @SerializedName("description")
    val description: Any, // null
    @SerializedName("doctor_id")
    val doctorId: Int, // 70
    @SerializedName("id")
    val id: Int, // 175
    @SerializedName("state")
    val state: Int, // 1
    @SerializedName("stateName")
    val stateName: String // Kutishda
)
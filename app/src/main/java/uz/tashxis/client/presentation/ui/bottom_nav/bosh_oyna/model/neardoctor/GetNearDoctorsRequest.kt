package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetNearDoctorsRequest(
    @SerializedName("lat")
    val lat: Double, // 40
    @SerializedName("long")
    val long: Double, // 70
    @SerializedName("distance")
    val distance: Int // 1000
)
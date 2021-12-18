package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Region(
    @SerializedName("id")
    val id: Int, // 31
    @SerializedName("name")
    val name: String, // Andijon tumani
    @SerializedName("region_id")
    val regionId: Int // 2
)
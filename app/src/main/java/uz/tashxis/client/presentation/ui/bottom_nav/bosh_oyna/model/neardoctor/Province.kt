package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Province(
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("name")
    val name: String // Andijon viloyati
)
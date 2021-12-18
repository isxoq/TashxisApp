package uz.tashxis.client.presentation.ui.auth.model.auth


import com.google.gson.annotations.SerializedName

data class RegionData(
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("name")
    val name: String // Qoraqalpog‘iston Respublikasi
)
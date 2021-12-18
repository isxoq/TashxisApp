package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response


import com.google.gson.annotations.SerializedName

data class DoctorResponseData(
    @SerializedName("acceptance_amount")
    val acceptanceAmount: Int?, // 30000
    @SerializedName("achievements")
    val achievements: String?, // КвалификацияВрач высшей категории
    @SerializedName("distance")
    val distance: Double?, // 2.3
    @SerializedName("father_name")
    val fatherName: String?, // Kamilevna
    @SerializedName("first_name")
    val firstName: String?, // Nailya
    @SerializedName("hospital")
    val hospital: Hospital?,
    @SerializedName("id")
    val id: Int?, // 70
    @SerializedName("imageUrl")
    val imageUrl: String?, // /uploads/images/doctor/70/preview-61512b3737917.png
    @SerializedName("last_name")
    val lastName: String?, // O'zbekova
    @SerializedName("patients")
    val patients: Int?, // 10
    @SerializedName("province")
    val province: Province?,
    @SerializedName("quarter")
    val quarter: Any?, // null
    @SerializedName("rate")
    val rate: Int?, // 4
    @SerializedName("region")
    val region: Region?,
    @SerializedName("speciality")
    val speciality: Speciality?,
    @SerializedName("study")
    val study: String?, // Тип обучения: Высшее учебное заведениеФакультет/Направление/Отделение: Кардиология и терапияСпециализация: Терапевт-кардиологУчебное заведение: Алма-Атинский государственный медицинский институтПериод обучения: 1983 - 1986
    @SerializedName("work_year")
    val workYear: Int? // 15
)
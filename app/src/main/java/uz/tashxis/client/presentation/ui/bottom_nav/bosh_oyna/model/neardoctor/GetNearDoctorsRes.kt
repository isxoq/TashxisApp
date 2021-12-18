package uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetNearDoctorsRes(
    @SerializedName("acceptance_amount")
    val acceptanceAmount: Int, // 20000
    @SerializedName("achievements")
    val achievements: String, // 1993г. - прошел усовершенствование по курсу» Современные методы лечения осложнений кариеса» по терапевтической стоматологии2001г. - прошел полный курс обучения «Современные методы лечения заболеваний слизистой полости рта».2001г. - получил 1 категорию2002г. - прошел усовершенствование по курсу «Лечение зубов современными пломбировочными массами»2008г. - прошел специализацию по «Ортопедической стоматологии по теме «Современные методы протезирования»2008г. - присвоена высшая квалификационная категория по специальности стоматология.2008г.- был участником IV-го Ташкентского Международного научного форума стоматологов
    @SerializedName("distance")
    val distance: Double, // 2.3
    @SerializedName("father_name")
    val fatherName: String, // Hasanovich
    @SerializedName("first_name")
    val firstName: String, // Furqat
    @SerializedName("hospital")
    val hospital: Hospital,
    @SerializedName("id")
    val id: Int, // 68
    @SerializedName("imageUrl")
    val imageUrl: String, // /uploads/images/doctor/68/preview-61512a13e1c2e.jpg
    @SerializedName("last_name")
    val lastName: String, // To'ychiyev
    @SerializedName("patients")
    val patients: Int, // 10
    @SerializedName("province")
    val province: Province,
    @SerializedName("quarter")
    val quarter: Any, // null
    @SerializedName("rate")
    val rate: Int, // 4
    @SerializedName("region")
    val region: Region,
    @SerializedName("speciality")
    val speciality: Speciality?,
    @SerializedName("study")
    val study: String, // В 1984 году закончил Ташкентский Государственный Медицинский Институт. Стоматологический факультет.
    @SerializedName("work_year")
    val workYear: Int // 6
)
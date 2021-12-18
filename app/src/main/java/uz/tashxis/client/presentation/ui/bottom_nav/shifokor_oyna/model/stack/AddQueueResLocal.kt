package uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack


data class AddQueueResLocal(
    val id: Long,
    val queueId: String,
    val doctorImage: String?,
    val doctorFirstName: String?,
    val doctorLastName: String?,
    val doctorSpeciality: String?,
    val doctorAddress: String?,
    val date: String,
    val time: String
)
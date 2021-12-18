package uz.tashxis.client.framework.repo

import uz.tashxis.client.data.Api
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor.GetNearDoctorsRequest
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueRequest

class MainRepository(private val service: Api) {

    suspend fun getSpeciality() = service.getSpecialty()

    suspend fun getDoctors(id_speciality: Int) = service.getDoctors(id_speciality)

    suspend fun getAboutDoctor(id: Int) = service.getAboutDoctor(id)

    suspend fun getAcceptDays(doctorId: Int) = service.getAcceptDays(doctorId)

    suspend fun getAcceptTimes(doctorId: Int) = service.getAcceptTimes(doctorId)

    suspend fun stackCommit(token: String, addQueueRequest: AddQueueRequest) =
        service.putStackCommit(token, addQueueRequest)

    suspend fun getNearDoctors(getNearDoctorsRequest: GetNearDoctorsRequest) =
        service.getNearDoctors(getNearDoctorsRequest)
}
package com.example.tashxis.framework.repo

import com.example.tashxis.data.Api
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueRequest

class MainRepository(val service: Api) {

    suspend fun getSpeciality() = service.getSpecialty()

    suspend fun getDoctors(id_speciality: Int) = service.getDoctors(id_speciality)

    suspend fun getAboutDoctor(id: Int) = service.getAboutDoctor(id)

    suspend fun getAcceptDays(doctorId: Int) = service.getAcceptDays(doctorId)

    suspend fun getAcceptTimes(doctorId: Int) = service.getAcceptTimes(doctorId)

    suspend fun stackCommit(token: String, addQueueRequest: AddQueueRequest) =
        service.putStackCommit(token, addQueueRequest)

}
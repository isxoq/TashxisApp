package com.example.tashxis.framework.repo

import com.example.tashxis.data.Api

class MainRepository(val service: Api) {

    suspend fun getSpeciality() = service.getSpecialty()
    suspend fun getDoctors(id_speciality: Int) = service.getDoctors(id_speciality)
    suspend fun getAboutDoctor(id: Int) = service.getAboutDoctor(id)

}
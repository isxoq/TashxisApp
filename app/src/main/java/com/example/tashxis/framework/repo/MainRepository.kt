package com.example.tashxis.framework.repo

import com.example.tashxis.data.Api

class MainRepository(val service: Api) {

    suspend fun getSpeciality() = service.getSpecialty()


}
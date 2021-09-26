package com.example.tashxis.framework.repo

import com.example.tashxis.data.Api

class AuthRepository(val service: Api) {

    suspend fun register(phone: String) = service.register(phone)

    suspend fun verify_code(phone: String, code: String) = service.verify_code(phone, code)

    suspend fun add_profile_info_response(
        auth_key: String,
        first_name: String,
        last_name: String,
        father_name: String,
        gender: Int,
        province_id: Int,
        region_id: Int,
        birth_date: String
    ) = service.add_profile_info(
        auth_key,
        first_name,
        last_name,
        father_name,
        gender,
        province_id,
        region_id,
        birth_date
    )

    suspend fun login(phone: String) = service.login(phone)

    suspend fun login_verify(phone: String, code: String) = service.login_verify(phone, code)

    suspend fun getRegions() = service.getRegions()

    suspend fun getDistrict(region_id: Int) = service.getDistrict(region_id)


}
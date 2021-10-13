package com.example.tashxis.data

import com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse.DistrictData
import com.example.tashxis.presentation.ui.auth.model.auth.LoginResponseData
import com.example.tashxis.presentation.ui.auth.model.auth.ProfileInfoResponse.ProfileInFoData
import com.example.tashxis.presentation.ui.auth.model.auth.RegionData
import com.example.tashxis.presentation.ui.auth.model.auth.RegisterResponseData
import com.example.tashxis.presentation.ui.auth.model.auth.VerifyCodeData
import com.example.tashxis.presentation.ui.auth.model.auth.login_verify.LoginVerifyData
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.speciality_response.SpecialityData
import retrofit2.Response
import retrofit2.http.*

interface Api {
    //register so'rov
    @FormUrlEncoded
    @POST("api/client/user/register")
    suspend fun register(
        @Field("phone") number: String
    ): Response<BaseDomen<RegisterResponseData>>

    //verify_code
    @FormUrlEncoded
    @POST("/api/client/user/verify-code")
    suspend fun verify_code(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Response<BaseDomen<VerifyCodeData>>

    //add_profile_info
    @FormUrlEncoded
    @POST("/api/client/user/add-profile-info")
    suspend fun add_profile_info(
        @Field("auth_key") auth_key: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("father_name") father_name: String,
        @Field("gender") gender: Int,
        @Field("province_id") province_id: Int,
        @Field("region_id") region_id: Int,
        @Field("birth_date") birth_date: String,
    ): Response<BaseDomen<ProfileInFoData>>

    // login
    @FormUrlEncoded
    @POST("/api/client/user/login")
    suspend fun login(
        @Field("phone") number: String
    ): Response<BaseDomen<LoginResponseData>>

    //login_verify
    @FormUrlEncoded
    @POST("/api/client/user/login-verify")
    suspend fun login_verify(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Response<BaseDomen<LoginVerifyData>>

    @GET("/api/client/user/regions")
    suspend fun getRegions(
    ): Response<BaseDomen<List<RegionData>>>

    @GET("/api/client/user/districts?region_id")
    suspend fun getDistrict(
        @Query("region_id") region_id: Int
    ): Response<BaseDomen<List<DistrictData>>>



    @GET("/api/client/speciality/index")
    suspend fun getSpecialty(
    ): Response<BaseDomen<List<SpecialityData>>>

    @GET("/api/client/speciality/doctors?")
    suspend fun getDoctors(
        @Query("speciality_id") speciality_id: Int
    ): Response<BaseDomen<List<DoctorResponseData>>>

    @GET("/api/client/doctor/view?")
    suspend fun getAboutDoctor(
        @Query("id") id: Int
    ): Response<BaseDomen<AboutDoctorResponseData>>


}
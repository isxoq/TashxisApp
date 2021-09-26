package com.example.tashxis.data

import com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse.DistrictResponse
import com.example.tashxis.presentation.ui.auth.model.auth.LoginResponse.LoginResponse
import com.example.tashxis.presentation.ui.auth.model.auth.RegionResponse.RegionResponse
import com.example.tashxis.presentation.ui.auth.model.auth.RegisterResponse
import com.example.tashxis.presentation.ui.auth.model.auth.VerifyCodeResponse.VerifyCodeResponse
import com.example.tashxis.presentation.ui.auth.model.auth.login_verify.LoginVerifyResponse
import com.example.tashxis.presentation.ui.auth.model.auth.verify_code_response_final.VerifyCodeResponseFinal
import com.example.tashxis.presentation.ui.auth.model.main.SpecialityResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {
    //register so'rov
    @FormUrlEncoded
    @POST("api/client/user/register")
    suspend fun register(
        @Field("phone") number: String
    ): Response<RegisterResponse>

    //verify_code
    @FormUrlEncoded
    @POST("/api/client/user/verify-code")
    suspend fun verify_code(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Response<VerifyCodeResponseFinal>

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
    ): Response<VerifyCodeResponse>

    // login
    @FormUrlEncoded
    @POST("/api/client/user/login")
    suspend fun login(
        @Field("phone") number: String
    ): Response<LoginResponse>

    //login_verify
    @FormUrlEncoded
    @POST("/api/client/user/login-verify")
    suspend fun login_verify(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Response<LoginVerifyResponse>

    @FormUrlEncoded
    @GET("/api/client/user/regions")
    suspend fun getRegions(
    ): Response<RegionResponse>

    @FormUrlEncoded
    @GET("/api/client/user/districts?region_id")
    suspend fun getDistrict(
        @Query("region_id") region_id: Int
    ): Response<DistrictResponse>

    @FormUrlEncoded
    @GET("/api/client/speciality/index")
    suspend fun getSpecialty(

    ): Response<SpecialityResponse>
}
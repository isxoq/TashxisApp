package com.example.tashxis.data

import com.example.tashxis.ui.auth.model.VerifyCodeResponse.VerifyCodeResponse
import com.example.tashxis.ui.auth.model.login_verify.LoginVerifyResponse
import com.example.tashxis.ui.auth.model.test2.Loginresponse2
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    //register so'rov
    @FormUrlEncoded
    @POST("api/client/user/register")
    suspend fun register(
        @Field("phone") number: String
    ): Response<Loginresponse2>

    //verify_code
    @FormUrlEncoded
    @POST("/api/client/user/verify-code")
    suspend fun verify_code(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Response<VerifyCodeResponse>

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
    ): Response<Loginresponse2>

    //login_verify
    @FormUrlEncoded
    @POST("/api/client/user/login-verify")
    suspend fun login_verify(
        @Field("phone") phone: String,
        @Field("code")  code: String
    ) : Response<LoginVerifyResponse>
}
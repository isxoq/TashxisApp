package uz.tashxis.client.data

import uz.tashxis.client.presentation.ui.auth.model.auth.DistrictResponse.DistrictData
import uz.tashxis.client.presentation.ui.auth.model.auth.LoginResponseData
import uz.tashxis.client.presentation.ui.auth.model.auth.ProfileInfoResponse.ProfileInFoData
import uz.tashxis.client.presentation.ui.auth.model.auth.RegionData
import uz.tashxis.client.presentation.ui.auth.model.auth.RegisterResponseData
import uz.tashxis.client.presentation.ui.auth.model.auth.VerifyCodeData
import uz.tashxis.client.presentation.ui.auth.model.auth.login_verify.LoginVerifyData
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor.GetNearDoctorsRequest
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor.GetNearDoctorsRes
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.speciality.SpecialData
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueRequest
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResponse
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.StackDaysData
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
    ): Response<BaseDomen<List<SpecialData>>>

    @GET("/api/client/speciality/doctors?")
    suspend fun getDoctors(
        @Query("speciality_id") speciality_id: Int
    ): Response<BaseDomen<List<DoctorResponseData>>>

    @GET("/api/client/doctor/view?")
    suspend fun getAboutDoctor(
        @Query("id") id: Int
    ): Response<BaseDomen<AboutDoctorResponseData>>

    @GET("/api/client/doctor/accept-days?")
    suspend fun getAcceptDays(
        @Query("id") id: Int
    ): Response<BaseDomen<List<StackDaysData>>>

    @GET("/api/client/doctor/accept-times?")
    suspend fun getAcceptTimes(
        @Query("id") id: Int
    ): Response<BaseDomen<List<String>>>

    @POST("api/client/queue/add")
    suspend fun putStackCommit(
        @Header("X-Api-Key") token: String,
        @Body addQueueRequest: AddQueueRequest
    ): Response<BaseDomen<AddQueueResponse>>

    //Get Near Doctors
    @POST("/api/client/doctor/nearest-doctors")
    suspend fun getNearDoctors(
       @Body getNearDoctorsRequest: GetNearDoctorsRequest
    ): Response<BaseDomen<List<GetNearDoctorsRes>>>
}
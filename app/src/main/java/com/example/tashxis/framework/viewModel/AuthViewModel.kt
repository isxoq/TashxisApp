package com.example.tashxis.framework.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.App
import com.example.tashxis.business.util.*
import com.example.tashxis.data.BaseDomen
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse.DistrictData
import com.example.tashxis.presentation.ui.auth.model.auth.ProfileInfoResponse.ProfileInFoData
import com.example.tashxis.presentation.ui.auth.model.auth.RegionData
import com.example.tashxis.presentation.ui.auth.model.auth.login_verify.LoginVerifyData
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper
import kotlinx.coroutines.launch

interface IAuthViewModel {
    val liveState: LiveData<Status>
    val liveLoginState: LiveData<Status>
    val liveLoginVerifyState: LiveData<Status>
    val liveRegionState: LiveData<NetworkStatus<List<RegionData>>>
    val liveDistrictState: LiveData<NetworkStatus<List<DistrictData>>>
    val liveProfileInfoState: LiveData<NetworkStatus<ProfileInFoData>>
    val regionList: LiveData<Any>
    val districtList: LiveData<Any>
    val toast: LiveData<String>
    val phoneNumber: LiveData<String>
    val logReg: LiveData<Int>
    val token: LiveData<String>
    val _timer: LiveData<String>
    val imageUri: LiveData<String>
    fun setImageUri(uri: String)
    fun register(
        phone: String
    )

    fun getRegion()
    fun getDistrict(region_id: Int)

    fun verifyCode(phone: String, code: String)
    fun addProfileInfo(
        auth_key: String,
        first_name: String,
        last_name: String,
        father_name: String,
        gender: Int,
        province_id: Int,
        region_id: Int,
        birth_date: String
    )

    fun login(phone: String)
    fun loginVerify(phone: String, code: String)
    fun setPreferences(data: Any)
//    fun storeLoginPreference(data: Data)

}

class AuthViewModel(
    private val authRepository: AuthRepository,
    app: Application
) : AndroidViewModel(app), IAuthViewModel {
    val TAG = "TAG"

    private val preferences by lazyFast { PrefHelper.getPref(app) }

    override val liveState = SingleLiveEvent<Status>()
    override val liveLoginState = SingleLiveEvent<Status>()
    override val liveLoginVerifyState = SingleLiveEvent<Status>()
    override val liveRegionState = MutableLiveData<NetworkStatus<List<RegionData>>>()
    override val liveDistrictState = MutableLiveData<NetworkStatus<List<DistrictData>>>()
    override val liveProfileInfoState = MutableLiveData<NetworkStatus<ProfileInFoData>>()
    override val regionList = MutableLiveData<Any>()
    override val districtList = MutableLiveData<Any>()
    override val toast = SingleLiveEvent<String>()

    override val phoneNumber = MutableLiveData<String>()
    override val logReg = MutableLiveData<Int>()
    override val token = MutableLiveData<String>()
    override val _timer = MutableLiveData<String>()
    override val imageUri = MutableLiveData<String>()

    override fun setImageUri(uri: String) {
        imageUri.postValue(uri)
        Toast.makeText(App.context!!, "AuthViewModelda ishlavatti $uri", Toast.LENGTH_SHORT).show()
    }

    //registratsiya so'rovi
    override fun register(phone: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "register: Loading")
                val result = authRepository.register(phone)
                liveState.postValue(Status.LOADING)
                if (result.isSuccessful) {
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            toast.postValue(result.body()!!.message)
                            liveState.postValue(Status.SUCCESS)
                            Log.d(TAG, "register: SMS code sent")
                            phoneNumber.postValue(phone)
                            logReg.postValue(Constants.REG)
                        }
                        BaseDomen.CLIENT_ALREADY_EXIST -> toast.postValue(result.body()!!.message)
                        BaseDomen.SMS_ALREADY_SENT -> toast.postValue(result.body()!!.message)
                    }
                } else {
                    Log.d(TAG, "register: Register Result is not successful")
                    toast.postValue(result.body()!!.message)
                    liveState.postValue(Status.ERROR)
                }
            } catch (e: Exception) {
                Log.d(TAG, "register: ${e.message}")
                liveState.postValue(Status.ERROR)
            }
        }
    }


    //GetRegion
    override fun getRegion() {
        viewModelScope.launch {
            try {
                liveRegionState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getRegions()
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (body.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                liveRegionState.postValue(NetworkStatus.SUCCESS(data))
                                Log.d(TAG, "getRegion: Success")
                            } else {
                                liveRegionState.postValue(NetworkStatus.ERROR("null"))
                                Log.d(TAG, "getRegion: Success")
                                toast.postValue("Null")
                            }
                        }

                    }

                } else {
                    liveRegionState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    toast.postValue(result.body()!!.message)
                }

            } catch (e: Exception) {
                liveRegionState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Log.d(TAG, "getRegion: ${e.message}")
            }

        }


    }

    //getDistrict
    override fun getDistrict(region_id: Int) {
        viewModelScope.launch {
            try {
                liveDistrictState.postValue(NetworkStatus.LOADING())
                Log.d(TAG, "getDistrict: Loading")
                val result = authRepository.getDistrict(region_id)
                val body = result.body()!!
                when (body.code) {
                    BaseDomen.SUCCESS -> {
                        if (body.data != null) {
                            liveDistrictState.postValue(NetworkStatus.SUCCESS(body.data))
                            Log.d(TAG, "getDistrict: Success")
                        } else {
                            liveDistrictState.postValue(NetworkStatus.ERROR("data = null"))
                            Log.d(TAG, "getDistrict: Error")
                        }
                    }
                }

            } catch (e: Exception) {
                liveDistrictState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                Log.d(TAG, "getDistrict: ${e.message}")
                toast.postValue(e.message)
            }
        }
    }

    // Add profile info
    override fun addProfileInfo(
        auth_key: String,
        first_name: String,
        last_name: String,
        father_name: String,
        gender: Int,
        province_id: Int,
        region_id: Int,
        birth_date: String
    ) {
        viewModelScope.launch {
            try {
                val result = authRepository.add_profile_info_response(
                    auth_key,
                    first_name,
                    last_name,
                    father_name,
                    gender,
                    province_id,
                    region_id,
                    birth_date
                )
                liveProfileInfoState.postValue(NetworkStatus.LOADING())
                if (result.isSuccessful) {
                    val body = result.body()
                    if (body != null) {
                        val data = body.data
                        when (body.code) {
                            BaseDomen.SUCCESS -> {
                                if (data != null) {
                                    liveProfileInfoState.postValue(NetworkStatus.SUCCESS(body.data))
                                    //TODO
                                    setPreferences(data)
//                                preference ga mashetta saqlab qo'ya qolamiz'
                                } else {
                                    liveProfileInfoState.postValue(NetworkStatus.ERROR("Null"))
                                }
                            }
                        }
                    } else {
                        liveProfileInfoState.postValue(NetworkStatus.ERROR("Body is null"))
                    }
                } else {
                    liveProfileInfoState.postValue(NetworkStatus.ERROR("Result is not successful"))

                }
            } catch (e: Exception) {
                liveProfileInfoState.postValue(NetworkStatus.ERROR("Tapping exception ${e.message}"))
            }
        }
    }

    //login
    override fun login(phone: String) {
        viewModelScope.launch {
            try {
                liveLoginState.postValue(Status.LOADING)
                val result = authRepository.login(phone)
                if (result.isSuccessful) {
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            liveLoginState.postValue(Status.SUCCESS)
                            phoneNumber.postValue(phone)
                            logReg.postValue(Constants.LOG)

                        }
                        BaseDomen.CLIENT_NOT_FOUND -> {
                            liveLoginState.postValue(Status.ERROR)
                        }
                        BaseDomen.SMS_ALREADY_SENT -> {
                            liveLoginState.postValue(Status.SUCCESS)
                        }
                    }
                } else {
                    liveLoginState.postValue(Status.ERROR)
                }
            } catch (e: Exception) {
                liveLoginState.postValue(Status.ERROR)
            }
        }
    }


    //verify_code
    override fun verifyCode(phone: String, code: String) {
        viewModelScope.launch {
            try {
                liveLoginVerifyState.postValue(Status.LOADING)
                val result = authRepository.verify_code(phone, code)
                if (result.isSuccessful) {
                    val body = result.body()!!
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            liveLoginVerifyState.postValue(Status.SUCCESS)
                            Log.d(TAG, "login: SMS code sent")
                            phoneNumber.postValue(phone)
                            logReg.postValue(Constants.LOG)
                            if (body.data != null) {
                                preferences.token = body.data.authKey
                                preferences.phone = body.data.phone
                                Log.d("TAGTAG", "verify_code: ${preferences.token}")
                            } else {
                                toast.postValue("Data is null")
                            }
                        }
                        BaseDomen.CLIENT_NOT_FOUND -> {
                            toast.postValue(result.body()!!.message)
                            liveLoginVerifyState.postValue(Status.ERROR)
                            Log.d(TAG, "login: Not found")
                        }
                    }

                } else {
                    toast.postValue(result.message())
                }
            } catch (e: Exception) {
                liveLoginVerifyState.postValue(Status.ERROR)
                toast.postValue(e.message)
            }

        }
    }

    // login_verify
    override fun loginVerify(phone: String, code: String) {
        viewModelScope.launch {
            try {
                liveLoginVerifyState.postValue(Status.LOADING)
                val result = authRepository.login_verify(phone, code)
                if (result.isSuccessful) {
                    val body = result.body()
                    Log.d(TAG, "result body: $body")
                    if (body != null) {
                        val data = body.data
                        Log.d(TAG, "body data: $data")
                        when (result.body()!!.code) {
                            BaseDomen.SUCCESS -> {
                                liveLoginVerifyState.postValue(Status.SUCCESS)
                                setPreferences(data!!)
                            }
                            BaseDomen.CLIENT_NOT_FOUND -> {
                                liveLoginVerifyState.postValue(Status.ERROR)
                                toast.postValue("Bazada bunday raqam topilmadi")
                            }
                            BaseDomen.INVALID_OTP -> {
                                liveLoginVerifyState.postValue(Status.ERROR)
                                toast.postValue("Noto'g'ri parol kiritildi")
                            }
                            BaseDomen.OTP_EXPIRED -> {
                                liveLoginVerifyState.postValue(Status.ERROR)
                                toast.postValue("Parol Muddati o'tgan")
                            }
                        }

                    } else {

                        liveState.postValue(Status.ERROR)
                        toast.postValue(result.body()!!.message)

                    }
                } else {
                    liveState.postValue(Status.ERROR)
                    toast.postValue(result.body()!!.message)
                }

            } catch (e: Exception) {
                liveState.postValue(Status.ERROR)
                toast.postValue(e.message)
            }

        }

    }

    override fun setPreferences(data: Any) {
        if (data is LoginVerifyData) {
            preferences.token = data.authKey
            preferences.phone = data.phone
            preferences.name = data.firstName
            preferences.surename = data.lastName
            preferences.fathername = data.fatherName
            preferences.birthDate = data.birthDate
            preferences.provinceId = data.provinceId
            preferences.regionId = data.regionId
            preferences.id = data.id ?: 1029209
            preferences.gender.let {
                if (data.gender == 1) {
                    "Erkak"
                } else {
                    "Ayol"
                }
            }
            Log.d(TAG, "login_verify: Working After pref")
        }
        if (data is ProfileInFoData) {
            preferences.token = data.authKey
            preferences.phone = data.phone
            preferences.name = data.firstName
            preferences.surename = data.lastName
            preferences.fathername = data.fatherName
            preferences.birthDate = data.birthDate
            preferences.provinceId = data.provinceId?.toInt()
            preferences.regionId = data.regionId?.toInt()
            preferences.gender = data.gender.toString()
            Log.d(TAG, "login_verify: Working After pref")
        }

    }

/*
    private fun setPreferences(data: LoginVerifyData?) {
        preferences.phone = data?.phone
        preferences.name = data?.firstName
        preferences.surename = data?.lastName
        preferences.fathername = data?.fatherName
        preferences.birthDate = data?.birthDate
        preferences.provinceId = data?.provinceId
        preferences.regionId = data?.regionId
        Log.d(TAG, "login_verify: Working After pref")
    }
*/


//    override fun storeLoginPreference(data: Data) {
//        preferences.authToken = data.authKey
//        preferences.birthDate = data.birthDate
//        preferences.firstName = data.firstName
//        preferences.fatherName = data.fatherName
//        preferences.lastName = data.lastName
//        preferences.phone = data.phone
//        preferences.createdAt = data.createdAt
//        preferences.email = data.email
//        preferences.gender = data.gender
//        preferences.id = data.id
//        preferences.image = data.image
//        preferences.username = data.username
//        preferences.regionId = data.regionId
//        preferences.typeId = data.typeId
//        preferences.updatedAt = data.typeId
//        preferences.status = data.status
//    }


}
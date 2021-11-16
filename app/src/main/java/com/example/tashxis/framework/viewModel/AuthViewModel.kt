package com.example.tashxis.framework.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.business.util.Constants
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.SingleLiveEvent
import com.example.tashxis.business.util.Status
import com.example.tashxis.data.BaseDomen
import com.example.tashxis.framework.repo.AuthRepository
import com.example.tashxis.presentation.ui.auth.model.auth.DistrictResponse.DistrictData
import com.example.tashxis.presentation.ui.auth.model.auth.ProfileInfoResponse.ProfileInFoData
import com.example.tashxis.presentation.ui.auth.model.auth.RegionData
import com.example.tashxis.presentation.ui.auth.preference.PrefHelper
import kotlinx.coroutines.launch

interface IAuthViewModel {
    val liveState: LiveData<Status>
    val liveLoginState: LiveData<Status>
    val liveLoginVerifyState: LiveData<Status>
    val regionList: LiveData<Any>
    val districtList: LiveData<Any>
    val toast: LiveData<String>
    val phoneNumber: LiveData<String>
    val logReg: LiveData<Int>
    val token: LiveData<String>
    val _timer: LiveData<String>
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
//    fun storeLoginPreference(data: Data)

}

class AuthViewModel(
    private val authRepository: AuthRepository,
    app: Application
) : AndroidViewModel(app), IAuthViewModel {

    val TAG = "TAG"

    private val preferences = PrefHelper.getPref(app)

    override val liveState = SingleLiveEvent<Status>()
    override val liveLoginState = SingleLiveEvent<Status>()
    override val liveLoginVerifyState = SingleLiveEvent<Status>()
    override val regionList
        get() = MutableLiveData<Any>()
    override val districtList
        get() = MutableLiveData<Any>()
    override val toast = SingleLiveEvent<String>()

    override val phoneNumber = MutableLiveData<String>()
    override val logReg = MutableLiveData<Int>()
    override val token: MutableLiveData<String>
        get() = MutableLiveData()
    override val _timer = MutableLiveData<String>()

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

    private val _liveRegionState = MutableLiveData<NetworkStatus<List<RegionData>>>()
    val liveRegionState: LiveData<NetworkStatus<List<RegionData>>> = _liveRegionState

    //GetRegion
    override fun getRegion() {
        viewModelScope.launch {
            try {
                _liveRegionState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getRegions()
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (body.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveRegionState.postValue(NetworkStatus.SUCCESS(data))
                                Log.d(TAG, "getRegion: Success")
                            } else {
                                _liveRegionState.postValue(NetworkStatus.ERROR("null"))
                                Log.d(TAG, "getRegion: Success")
                                toast.postValue("Null")
                            }
                        }

                    }

                } else {
                    _liveRegionState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    toast.postValue(result.body()!!.message)
                }

            } catch (e: Exception) {
                _liveRegionState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Log.d(TAG, "getRegion: ${e.message}")
            }

        }


    }

    //getDistrict
    private val _liveDistrictState = MutableLiveData<NetworkStatus<List<DistrictData>>>()
    val liveDistrictState: LiveData<NetworkStatus<List<DistrictData>>> = _liveDistrictState
    override fun getDistrict(region_id: Int) {
        viewModelScope.launch {
            try {
                _liveDistrictState.postValue(NetworkStatus.LOADING())
                Log.d(TAG, "getDistrict: Loading")
                val result = authRepository.getDistrict(region_id)
                val body = result.body()!!
                when (body.code) {
                    BaseDomen.SUCCESS -> {
                        if (body.data != null) {
                            _liveDistrictState.postValue(NetworkStatus.SUCCESS(body.data))
                            Log.d(TAG, "getDistrict: Success")
                        } else {
                            _liveDistrictState.postValue(NetworkStatus.ERROR("data = null"))
                            Log.d(TAG, "getDistrict: Error")
                        }
                    }
                }

            } catch (e: Exception) {
                _liveDistrictState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                Log.d(TAG, "getDistrict: ${e.message}")
                toast.postValue(e.message)
            }
        }
    }

    // Add profile info
    private val _liveProfileInfoState = MutableLiveData<NetworkStatus<ProfileInFoData>>()
    val liveProfileInfoState: LiveData<NetworkStatus<ProfileInFoData>> = _liveProfileInfoState

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
                _liveProfileInfoState.postValue(NetworkStatus.LOADING())
                if (result.isSuccessful) {
                    val body = result.body()

                    if (body != null) {
                        val data = body.data
                        when (body.code) {
                            BaseDomen.SUCCESS -> {
                                if (data != null) {
                                    _liveProfileInfoState.postValue(NetworkStatus.SUCCESS(body.data))
                                    //TODO
//                                preference ga mashetta saqlab qo'ya qolamiz'
                                    saveCredentials()
                                } else {
                                    _liveProfileInfoState.postValue(NetworkStatus.ERROR("Null"))
                                }
                            }
                        }
                    } else {
                        _liveProfileInfoState.postValue(NetworkStatus.ERROR("Body is null"))
                    }
                } else {
                    _liveProfileInfoState.postValue(NetworkStatus.ERROR("Result is not successful"))

                }
            } catch (e: Exception) {
                _liveProfileInfoState.postValue(NetworkStatus.ERROR("Tapping exception ${e.message}"))
            }
        }
    }

    private fun saveCredentials() {

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
                                Log.d(TAG, "login_verify: Working After pref")
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
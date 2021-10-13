package com.example.tashxis.framework.viewModel

import android.app.Application
import android.content.Context
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
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefs
import com.example.tashxis.presentation.ui.auth.preference.TashxisPrefsImpl
import kotlinx.coroutines.launch

interface IAuthViewModel {
    val liveState: LiveData<Status>
    val regionList: LiveData<Any>
    val districtList: LiveData<Any>
    val toast: LiveData<String>
    val phoneNumber: LiveData<String>
    val logReg: LiveData<Int>
    val _timer: LiveData<String>
    fun register(
        phone: String
    )

    fun getRegion()
    fun getDistrict(region_id: Int)

    fun verify_code(phone: String, code: String)
    fun add_profile_info(
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
    fun login_verify(phone: String, code: String)
    fun resend_registr_code()
//    fun storeLoginPreference(data: Data)

}

class AuthViewModel(
    private val authRepository: AuthRepository,
    app: Application
) : AndroidViewModel(app), IAuthViewModel {

    val TAG = "TAG"
    private var preferences: TashxisPrefs? = null

    init {
//        if (App.context != null) {
        val prefs = app.getSharedPreferences(
            Constants.PREF_NAME,
            Context.MODE_PRIVATE
        )
        preferences = TashxisPrefsImpl(prefs)
//        }
    }


    override val liveState = SingleLiveEvent<Status>()
    override val regionList
        get() = MutableLiveData<Any>()
    override val districtList
        get() = MutableLiveData<Any>()
    override val toast = SingleLiveEvent<String>()

    override val phoneNumber = MutableLiveData<String>()
    override val logReg = MutableLiveData<Int>()
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
                    when (body.code) {
                        BaseDomen.SUCCESS -> {
                            val data = body.data
                            _liveRegionState.postValue(NetworkStatus.SUCCESS(data))
                        }
                    }

                } else {
                    Log.d(TAG, "login: Register Result is not successful")
                    _liveRegionState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    toast.postValue(result.body()!!.message)
                }

            } catch (e: Exception) {
                Log.d(TAG, "login: Register Result is not error ${e.message}")
                _liveRegionState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
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
                val result = authRepository.getDistrict(region_id)
                Log.d(TAG, "getDistrict: Loading")
                val body = result.body()!!
                when (body.code) {
                    BaseDomen.SUCCESS -> {
                        if (body.data != null)
                            _liveDistrictState.postValue(NetworkStatus.SUCCESS(body.data))
                        else
                            _liveDistrictState.postValue(NetworkStatus.ERROR("data = null"))
                    }
                }

            } catch (e: Exception) {
                _liveDistrictState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Log.d(TAG, "getDistrict: catch working ${e.message}")
            }
        }
    }

    // Add profile info
    private val _liveProfileInfoState = MutableLiveData<NetworkStatus<ProfileInFoData>>()
    val liveProfileInfoState: LiveData<NetworkStatus<ProfileInFoData>> = _liveProfileInfoState
    override fun add_profile_info(
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
                        when (body.code) {
                            BaseDomen.SUCCESS -> {
                                _liveProfileInfoState.postValue(NetworkStatus.SUCCESS(body.data))
                            }
                        }
                    } else {
                        _liveProfileInfoState.postValue(NetworkStatus.ERROR("Body is null"))
                        Log.d(TAG, "add_profile_info: body is null")
                        toast.postValue("data is null")
                    }
                } else {
                    _liveProfileInfoState.postValue(NetworkStatus.ERROR("Result is not successful"))
                    Log.d(TAG, "add_profile_info: Result is not successful $result")
                    toast.postValue("Result is not successful")

                }
            } catch (e: Exception) {
                _liveProfileInfoState.postValue(NetworkStatus.ERROR("Tapping exception ${e.message}"))
                toast.postValue(e.message)
            }
        }
    }

    //login
    override fun login(phone: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.login(phone)
                liveState.postValue(Status.LOADING)
                if (result.isSuccessful) {
                    /*
                    when(result.body()!!.code)
                 {
                       BaseDomen.SUCCESS -> toast.postValue(result.body()!!.message)
                     BaseDomen.CLIENT_ALREADY_EXIST ->toast.postValue(result.body()!!.message)
                     BaseDomen.SMS_ALREADY_SENT ->toast.postValue(result.body()!!.message)
                 }
                 } else {
                     Log.d(TAG, "register: Register Result is not successful")
                     toast.postValue(result.body()!!.message)
                     liveState.postValue(Status.ERROR)
                 }
                     */

                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            toast.postValue(result.body()!!.message)
                            liveState.postValue(Status.SUCCESS)
                            Log.d(TAG, "login: SMS code sent")
                            phoneNumber.postValue(phone)
                            logReg.postValue(Constants.LOG)
                        }
                        BaseDomen.CLIENT_NOT_FOUND -> {
                            toast.postValue(result.body()!!.message)
                            liveState.postValue(Status.ERROR)
                            Log.d(TAG, "login: Not found")
                        }
                        BaseDomen.SMS_ALREADY_SENT -> {
                            toast.postValue(result.body()!!.message)
                            Log.d(TAG, "login: SMS ALReady Sent")
                        }
                    }
                } else {
                    Log.d(TAG, "login: Register Result is not successful")
                    liveState.postValue(Status.ERROR)
                    toast.postValue("ulanish bilan bog'liq xatolik yuz berdi")
                }
            } catch (e: Exception) {
                Log.d(TAG, "login: ${e.message}")
                liveState.postValue(Status.ERROR)
                toast.postValue(e.message)
            }
        }
    }


    //verify_code
    override fun verify_code(phone: String, code: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "verify_code: start try $phone  $code")
                val result = authRepository.verify_code(phone, code)
                if (result.isSuccessful) {
                    val body = result.body()!!
                    when (result.body()!!.code) {

                        BaseDomen.SUCCESS -> {
                            toast.postValue(result.body()!!.message)
                            liveState.postValue(Status.SUCCESS)
                            Log.d(TAG, "login: SMS code sent")
                            phoneNumber.postValue(phone)
                            logReg.postValue(Constants.LOG)
                            preferences?.token = body.data.authKey
                        }
                        BaseDomen.CLIENT_NOT_FOUND -> {
                            toast.postValue(result.body()!!.message)
                            liveState.postValue(Status.ERROR)
                            Log.d(TAG, "login: Not found")
                        }
                    }

                } else {

                    Log.d(TAG, "verify_code: Response is not sucessfull  ${result}")
                    toast.postValue(result.message())
                }
            } catch (e: Exception) {
                liveState.postValue(Status.ERROR)
                toast.postValue(e.message)
                Log.d(TAG, "verify_code: Chota xatoda shu ${e.message}")
            }

        }
    }

    // login_verify
    override fun login_verify(phone: String, code: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.login_verify(phone, code)
                Log.d(TAG, "result login: $result")

                if (result.isSuccessful) {
                    val body = result.body()
                    Log.d(TAG, "result body: $body")
                    if (body != null) {
                        val data = body.data
                        Log.d(TAG, "body data: $data")
                        //+9989364482
//                            UserCredentials.setUserCredentials(result.body()!!.data)
                        //storeLoginPreference(data)
                        when(result.body()!!.code)
                        {
                            BaseDomen.SUCCESS ->
                            {
                                liveState.postValue(Status.SUCCESS)
                                Log.d(TAG, "login: Nomer tasdiqlandi")
                            }
                            BaseDomen.CLIENT_NOT_FOUND ->
                            {
                                liveState.postValue(Status.ERROR)
                                Log.d(TAG, "login: Not found")
                                toast.postValue("Bazada bunday raqam topilmadi")
                            }
                            BaseDomen.INVALID_OTP ->
                            {
                                liveState.postValue(Status.ERROR)
                                Log.d(TAG, "login: Invalid OTP")
                                toast.postValue("Noto'g'ri parol kiritildi")
                            }
                            BaseDomen.OTP_EXPIRED ->
                            {
                                liveState.postValue(Status.ERROR)
                                Log.d(TAG, "login_verify: Otp Expired")
                                toast.postValue("Parol Muddati o'tgan")
                            }
                        }

                    } else {
                        Log.d(
                            TAG,
                            " ${result.body()!!.message} login: Message not equals Sms code sent"
                        )
                        liveState.postValue(Status.ERROR)
                        toast.postValue(result.body()!!.message)

                    }
                } else {
                    Log.d(TAG, "login: Login Result is not successful")
                    liveState.postValue(Status.ERROR)
                    toast.postValue(result.body()!!.message)
                }

            } catch (e: Exception) {
                Log.d(TAG, "login: error exception ${e.message}")
                liveState.postValue(Status.ERROR)
                toast.postValue(e.message)
            }

        }

    }


    override fun resend_registr_code() {
        TODO("Not yet implemented")
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
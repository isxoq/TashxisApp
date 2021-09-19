package com.example.tashxis.ui.auth.vm

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.Application
import com.example.tashxis.ui.auth.repo.AuthRepository
import com.example.tashxis.util.Constants
import com.example.tashxis.util.SingleLiveEvent
import com.example.tashxis.util.Status
import kotlinx.coroutines.launch

interface IAuthViewModel {
    val liveState: LiveData<Status>
    val toast: LiveData<String>
    val phoneNumber: LiveData<String>
    val logReg: LiveData<Int>
    val _timer: LiveData<String>
    fun register(
        phone: String,
    )

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
}

class AuthViewModel(
    private val authRepository: AuthRepository,
    application: Application
) : AndroidViewModel(application), IAuthViewModel {
    val TAG = "TAG"
    override val liveState = SingleLiveEvent<Status>()
    override val toast = SingleLiveEvent<String>()

    override val phoneNumber = MutableLiveData<String>()
    override val logReg = MutableLiveData<Int>()
    override val _timer = MutableLiveData<String>()

    override fun register(phone: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "register: Loading")
                liveState.postValue(Status.LOADING)
                val result = authRepository.register(phone)
                if (result.isSuccessful) {
                    if (result.body()!!.message == "code sent") {
                        liveState.postValue(Status.SUCCESS)
                        Log.d(TAG, "register: SMS code sent")
                        phoneNumber.postValue(phone)
                        logReg.postValue(Constants.REG)
                    } else if (result.body()!!.message == "Phone already registered") {
                        liveState.postValue(Status.ERROR)
                        Log.d(TAG, "register: Phone already registered")
                        toast.postValue("Bu raqam ro'yxatdan o'tgan")
                    } else {
                        Log.d(
                            TAG,
                            " ${result.body()!!.message} register: Message not equals Sms code sent"
                        )
                    }
                } else {
                    Log.d(TAG, "register: Register Result is not successful")
                    liveState.postValue(Status.ERROR)
                }
            } catch (e: Exception) {
                Log.d(TAG, "register: ${e.message}")
                liveState.postValue(Status.ERROR)
            }
        }
    }

    //login
    override fun login(phone: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.login(phone)
                if (result.isSuccessful) {
                    if (result.isSuccessful) {
                        if (result.body()!!.message == "code sent") {
                            liveState.postValue(Status.SUCCESS)
                            Log.d(TAG, "register: SMS code sent")
                            phoneNumber.postValue(phone)
                            logReg.postValue(Constants.LOG)
                        } else if (result.body()!!.message == "User not found") {
                            liveState.postValue(Status.ERROR)
                            Log.d(TAG, "login: Not found")
                            toast.postValue("Bazada bunday raqam topilmadi")
                        } else {
                            Log.d(
                                TAG,
                                " ${result.body()!!.message} login: Message not equals Sms code sent"
                            )
                        }
                    } else {
                        Log.d(TAG, "login: Register Result is not successful")
                        liveState.postValue(Status.ERROR)
                        toast.postValue("ulanish bilan bog'liq xatolik yuz berdi")
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "login: ${e.message}")
                liveState.postValue(Status.ERROR)
                toast.postValue(e.message)
            }
        }
    }
// Add profile info
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
                if (result.isSuccessful) {
                    if (result.isSuccessful) {
                        if (result.body()!!.message == "") {
                            liveState.postValue(Status.SUCCESS)
                            Log.d(TAG, "register: SMS code sent")
                        } else if (result.body()!!.message == "Not found") {
                            liveState.postValue(Status.ERROR)
                            Log.d(TAG, "login: Not found")
                            toast.postValue("Bazada bunday raqam topilmadi")
                        } else {
                            Log.d(
                                TAG,
                                " ${result.body()!!.message} login: Message not equals Sms code sent"
                            )
                        }
                    } else {
                        Log.d(TAG, "login: Register Result is not successful")
                        liveState.postValue(Status.ERROR)
                    }
                }
            } catch (e: Exception) {
            liveState.postValue(Status.ERROR)
            toast.postValue(e.message)
            }
        }
    }
//verify_code
    override fun verify_code(phone: String, code: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.verify_code(phone, code)
                if (result.isSuccessful) {
                    if (result.isSuccessful) {
                        if (result.body()!!.message == "") {
                            //TODO
                            liveState.postValue(Status.SUCCESS)
                            Log.d(TAG, "register: SMS code sent")
                        } else if (result.body()!!.message == "Not found") {
                            liveState.postValue(Status.ERROR)
                            Log.d(TAG, "login: Not found")
                            toast.postValue("Bazada bunday raqam topilmadi")
                        } else {
                            Log.d(
                                TAG,
                                " ${result.body()!!.message} login: Message not equals Sms code sent"
                            )
                            liveState.postValue(Status.ERROR)
                            toast.postValue(result.body()!!.message)
                        }
                    } else {
                        Log.d(TAG, "login: Register Result is not successful")
                        liveState.postValue(Status.ERROR)
                        toast.postValue(result.body()!!.message)
                    }
                }
            } catch (e: Exception) {
                liveState.postValue(Status.ERROR)
                toast.postValue(e.message)
            }

        }
    }

    override fun login_verify(phone: String, code: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.login_verify(phone, code)
                if (result.isSuccessful) {
                    if (result.isSuccessful) {
                        if (result.body()!!.message == "") {
                            //TODO
                            liveState.postValue(Status.SUCCESS)
                            Log.d(TAG, "register: SMS code sent")
                        } else if (result.body()!!.message == "Not found") {
                            liveState.postValue(Status.ERROR)
                            Log.d(TAG, "login: Not found")
                            toast.postValue("Bazada bunday raqam topilmadi")
                        } else {
                            Log.d(
                                TAG,
                                " ${result.body()!!.message} login: Message not equals Sms code sent"
                            )
                            liveState.postValue(Status.ERROR)
                            toast.postValue(result.body()!!.message)

                        }
                    } else {
                        Log.d(TAG, "login: Register Result is not successful")
                        liveState.postValue(Status.ERROR)
                        toast.postValue(result.body()!!.message)
                    }
                }
            } catch (e: Exception) {
            liveState.postValue(Status.ERROR)
                toast.postValue(e.message)
            }

        }

    }

    override fun resend_registr_code() {
        TODO("Not yet implemented")
    }


}
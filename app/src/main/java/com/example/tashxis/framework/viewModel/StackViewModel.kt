package com.example.tashxis.framework.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.SingleLiveEvent
import com.example.tashxis.data.BaseDomen
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.stack.StackDaysData
import kotlinx.coroutines.launch

class StackViewModel(
    private val authRepository: MainRepository,
    app: Application

) : AndroidViewModel(app) {

    private val _liveStackDayState = MediatorLiveData<NetworkStatus<List<StackDaysData>>>()
    private val _liveStackTimeState = MediatorLiveData<NetworkStatus<List<String>>>()
    val liveStackDayState: LiveData<NetworkStatus<List<StackDaysData>>> =
        _liveStackDayState
    val livestackTimeState: LiveData<NetworkStatus<List<String>>> =
        _liveStackTimeState
    val toast = SingleLiveEvent<String>()
    val TAG = "TAG"

    fun getStackDays(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getDoctorStackDays:Loading ")
                _liveStackDayState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getAcceptDays(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveStackDayState.postValue(NetworkStatus.SUCCESS(data))
                                Log.d(TAG, "getDoctorStackDays: Success")
                            } else {
                                _liveStackDayState.postValue(NetworkStatus.ERROR("data = null"))
                                toast.postValue("Data is null")
                            }
                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            _liveStackDayState.postValue(NetworkStatus.ERROR("UnknownError"))
                            toast.postValue("Unknown Error")
                        }

                    }
                } else {
                    _liveStackDayState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    Log.d(TAG, "getDoctorStackDays: Result is not succesfull ${result}")
                    toast.postValue("Result is not succesfull")
                }

            } catch (e: Exception) {
                _liveStackDayState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Log.d(TAG, "getDoctorStackDays: Exception tashavatti")
            }

        }
    }

    fun getStackTimes(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getDoctorStackTimes:Loading ")
                _liveStackTimeState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getAcceptTimes(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveStackTimeState.postValue(NetworkStatus.SUCCESS(data))
                                Log.d(TAG, "getDoctorStackTimes: Success")
                            } else {
                                _liveStackTimeState.postValue(NetworkStatus.ERROR("data = null"))
                                toast.postValue("Data is null")
                            }
                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            _liveStackTimeState.postValue(NetworkStatus.ERROR("UnknownError"))
                            toast.postValue("Unknown Error")
                        }

                    }
                } else {
                    _liveStackTimeState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    Log.d(TAG, "getDoctorStackTimes: Result is not succesfull ${result}")
                    toast.postValue("Result is not succesfull")
                }

            } catch (e: Exception) {
                _liveStackTimeState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Log.d(TAG, "getDoctorStackTimes: Exception tashavatti")
            }

        }
    }

    fun putStack(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getDoctorStackTimes:Loading ")
                _liveStackTimeState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getAcceptTimes(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveStackTimeState.postValue(NetworkStatus.SUCCESS(data))
                                Log.d(TAG, "getDoctorStackTimes: Success")
                            } else {
                                _liveStackTimeState.postValue(NetworkStatus.ERROR("data = null"))
                                toast.postValue("Data is null")
                            }
                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            _liveStackTimeState.postValue(NetworkStatus.ERROR("UnknownError"))
                            toast.postValue("Unknown Error")
                        }

                    }
                } else {
                    _liveStackTimeState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    Log.d(TAG, "getDoctorStackTimes: Result is not succesfull ${result}")
                    toast.postValue("Result is not succesfull")
                }

            } catch (e: Exception) {
                _liveStackTimeState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Log.d(TAG, "getDoctorStackTimes: Exception tashavatti")
            }

        }
    }
}
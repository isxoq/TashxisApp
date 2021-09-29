package com.example.tashxis.framework.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.SingleLiveEvent
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData
import kotlinx.coroutines.launch

class AboutDoctorViewModel(
    private val authRepository: MainRepository,
    app: Application

) : AndroidViewModel(app) {

    private val _liveAboutDoctorsState = MediatorLiveData<NetworkStatus<AboutDoctorResponseData>>()
    val liveAboutDoctorsState: LiveData<NetworkStatus<AboutDoctorResponseData>> =
        _liveAboutDoctorsState
    val toast = SingleLiveEvent<String>()
    val TAG ="TAG"

    fun getAboutDoctors(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getDoctors:Loading ")
                _liveAboutDoctorsState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getAboutDoctor(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    if (result.body()!!.message == "") {
                        val data = body.data
                        if (data != null) {
                            _liveAboutDoctorsState.postValue(NetworkStatus.SUCCESS(data))
                            Log.d(TAG, "getAboutDoctors: Success")
                        } else {
                            _liveAboutDoctorsState.postValue(NetworkStatus.ERROR("data = null"))

                        }
                    } else if (body.message == "Not found") {
                        _liveAboutDoctorsState.postValue(NetworkStatus.ERROR("Not found"))
                        Log.d(TAG, "getAboutDoctors: NotFound")
                    } else {
                        _liveAboutDoctorsState.postValue(NetworkStatus.ERROR(body.message ?: ""))
                        toast.postValue(body.message ?: "")

                    }
                } else {
                    _liveAboutDoctorsState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    Log.d(TAG, "getAboutDoctors: Result is not succesfull ${result}")
                }

            } catch (e: Exception) {
                _liveAboutDoctorsState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                Log.d(TAG, "getAboutDoctors: Exception tashavatti")
            }

        }
    }
}
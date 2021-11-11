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
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.about_doctor.AboutDoctorResponseData
import kotlinx.coroutines.launch

class AboutDoctorViewModel(
    private val authRepository: MainRepository,
    app: Application

) : AndroidViewModel(app) {

    private val _liveAboutDoctorsState = MediatorLiveData<NetworkStatus<AboutDoctorResponseData>>()
    val liveAboutDoctorsState: LiveData<NetworkStatus<AboutDoctorResponseData>> =
        _liveAboutDoctorsState
    val toast = SingleLiveEvent<String>()
    val TAG = "TAG"

    fun getAboutDoctors(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getDoctors:Loading ")
                _liveAboutDoctorsState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getAboutDoctor(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (body.code) {
                        BaseDomen.SUCCESS -> {
                            if (data!=null){
                                _liveAboutDoctorsState.postValue(NetworkStatus.SUCCESS(data))
                                toast.postValue(body.message)
                                Log.d(TAG, "getAboutDoctors: Success")
                            }
                            else{
                                _liveAboutDoctorsState.postValue(NetworkStatus.ERROR("Data is null"))
                                toast.postValue(body.message
                                )
                            }

                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            _liveAboutDoctorsState.postValue(NetworkStatus.ERROR("Unknown Error"))
                            toast.postValue("Unknown Error")
                        }
                        BaseDomen.FILE_DOES_NOT_EXIST -> {
                            _liveAboutDoctorsState.postValue(NetworkStatus.ERROR("File Does Not Excist"))
                            toast.postValue(body.message)
                        }

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
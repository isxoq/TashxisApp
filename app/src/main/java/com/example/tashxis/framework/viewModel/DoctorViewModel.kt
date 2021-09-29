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
import com.example.tashxis.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData
import kotlinx.coroutines.launch

class DoctorViewModel(
    private val authRepository: MainRepository,
    app: Application

) : AndroidViewModel(app) {

    private val _liveDoctorsState = MediatorLiveData<NetworkStatus<List<DoctorResponseData>>>()
    val liveDoctorsState: LiveData<NetworkStatus<List<DoctorResponseData>>> =
        _liveDoctorsState
    val toast = SingleLiveEvent<String>()
    val TAG ="TAG"

    fun getDoctors(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getDoctors:Loading ")
                _liveDoctorsState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getDoctors(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    if (result.body()!!.message == "") {
                        val data = body.data
                        if (data != null) {
                            _liveDoctorsState.postValue(NetworkStatus.SUCCESS(data))
                            Log.d(TAG, "getDoctors: Success")
                        } else {
                            _liveDoctorsState.postValue(NetworkStatus.ERROR("data = null"))

                        }
                    } else if (body.message == "Not found") {
                        _liveDoctorsState.postValue(NetworkStatus.ERROR("Not found"))
                        Log.d(TAG, "getDoctors: NotFound")
                    } else {
                        _liveDoctorsState.postValue(NetworkStatus.ERROR(body.message ?: ""))
                        toast.postValue(body.message ?: "")

                    }
                } else {
                    _liveDoctorsState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    Log.d(TAG, "getDoctors: Result is not succesfull ${result}")
                }

            } catch (e: Exception) {
                _liveDoctorsState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                Log.d(TAG, "getDoctors: Exception tashavatti")
            }

        }
    }
}
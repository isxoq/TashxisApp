package com.example.tashxis.framework.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.business.util.SingleLiveEvent
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.presentation.ui.auth.model.main.SpecialityData
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: MainRepository,
    app: Application

) : AndroidViewModel(app) {

    private val _liveSpecialityState = MediatorLiveData<NetworkStatus<List<SpecialityData>>>()
    val liveSpecialityState: LiveData<NetworkStatus<List<SpecialityData>>> =
        _liveSpecialityState
    val toast = SingleLiveEvent<String>()

    fun getSpeciality() {
        viewModelScope.launch {
            try {
                _liveSpecialityState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getSpeciality()
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    if (result.body()!!.message == "") {
                        val data = body.data
                        if (data != null) {
                            _liveSpecialityState.postValue(NetworkStatus.SUCCESS(data))
                        } else {
                            _liveSpecialityState.postValue(NetworkStatus.ERROR("data = null"))
                        }
                    } else if (body.message == "Not found") {
                        _liveSpecialityState.postValue(NetworkStatus.ERROR("Not found"))
                        toast.postValue("Bazada bunday raqam topilmadi")
                    } else {
                        _liveSpecialityState.postValue(NetworkStatus.ERROR(body.message ?: ""))
                        toast.postValue(body.message ?: "")

                    }
                } else {
                    _liveSpecialityState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    toast.postValue(result.body()!!.message ?: "")
                }

            } catch (e: Exception) {
                _liveSpecialityState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
            }

        }
    }
}
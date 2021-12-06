package com.example.tashxis.framework.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.business.util.NetworkStatus
import com.example.tashxis.data.BaseDomen
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.presentation.ui.bottom_nav.bosh_oyna.model.GetNearDoctorsRequest
import com.example.tashxis.presentation.ui.bottom_nav.bosh_oyna.model.GetNearDoctorsRes
import kotlinx.coroutines.launch

interface IGetNearDoctorsVM {
    val _liveState: LiveData<NetworkStatus<List<GetNearDoctorsRes>>>
    val liveState: LiveData<NetworkStatus<List<GetNearDoctorsRes>>>
    fun getNearDoctors()
}

class GetNearDoctorsVM(
    private val repository: MainRepository,
    application: Application
) :
    AndroidViewModel(application), IGetNearDoctorsVM {
    override val _liveState = MutableLiveData<NetworkStatus<List<GetNearDoctorsRes>>>()

    override val liveState: LiveData<NetworkStatus<List<GetNearDoctorsRes>>>
        get() = _liveState

    override fun getNearDoctors() {
        viewModelScope.launch {
            try {
                _liveState.postValue(NetworkStatus.LOADING())
                val result = repository.getNearDoctors(
                    GetNearDoctorsRequest(
                        1000,
                        1231232,
                        1000
                    )
                )
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (body.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveState.postValue(NetworkStatus.SUCCESS(listOf(data)))
                            } else {
                                _liveState.postValue(NetworkStatus.ERROR("data=null"))
                            }
                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            _liveState.postValue(NetworkStatus.ERROR("UnknownError"))
                        }
                    }

                }
            } catch (e: Exception) {
                _liveState.postValue(NetworkStatus.ERROR(e.message.toString()))
            }
        }
    }
}
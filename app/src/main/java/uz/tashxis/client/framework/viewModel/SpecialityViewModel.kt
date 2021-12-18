package uz.tashxis.client.framework.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.business.util.SingleLiveEvent
import uz.tashxis.client.data.BaseDomen
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.speciality.SpecialData
import kotlinx.coroutines.launch

class SpecialityViewModel(
    private val authRepository: MainRepository,
    app: Application
) : AndroidViewModel(app) {

    private val _liveSpecialityState = MediatorLiveData<NetworkStatus<List<SpecialData>>>()
    val liveSpecialityState: LiveData<NetworkStatus<List<SpecialData>>> = _liveSpecialityState
    val toast = SingleLiveEvent<String>()

    fun getSpeciality() {
        viewModelScope.launch {
            try {
                _liveSpecialityState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getSpeciality()
                if (result.isSuccessful) {
                    val body = result.body()
                    val data = body?.data
                    if (data != null) {
                        when (body.code) {
                            BaseDomen.SUCCESS -> {
                                _liveSpecialityState.postValue(NetworkStatus.SUCCESS(data))
                            }
                            BaseDomen.UNKNOWN_ERROR -> {
                                _liveSpecialityState.postValue(NetworkStatus.ERROR("Unknown Error"))
                                toast.postValue("Unknown error")
                            }

                        }

                    } else {
                        _liveSpecialityState.postValue(NetworkStatus.ERROR("data is null"))
                        toast.postValue(body?.message)
                    }
                } else {
                    _liveSpecialityState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    toast.postValue(result.body()?.message)
                }

            } catch (e: Exception) {
                _liveSpecialityState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
            }

        }
    }
}
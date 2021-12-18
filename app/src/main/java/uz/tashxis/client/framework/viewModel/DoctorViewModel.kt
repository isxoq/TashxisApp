package uz.tashxis.client.framework.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.business.util.SingleLiveEvent
import uz.tashxis.client.data.BaseDomen
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.doctor_response.DoctorResponseData
import kotlinx.coroutines.launch

class DoctorViewModel(
    private val authRepository: MainRepository,
    app: Application

) : AndroidViewModel(app) {

    private val _liveDoctorsState = MediatorLiveData<NetworkStatus<List<DoctorResponseData>>>()
    val liveDoctorsState: LiveData<NetworkStatus<List<DoctorResponseData>>> =
        _liveDoctorsState
    val toast = SingleLiveEvent<String>()
    val TAG = "TAG"

    fun getDoctors(id: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getDoctors:Loading ")
                _liveDoctorsState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getDoctors(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveDoctorsState.postValue(NetworkStatus.SUCCESS(data))
                                Log.d(TAG, "getDoctors: Success")
                            } else {
                                _liveDoctorsState.postValue(NetworkStatus.ERROR("data = null"))
                                toast.postValue("Data is null")
                            }
                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            _liveDoctorsState.postValue(NetworkStatus.ERROR("UnknownError"))
                            toast.postValue("Unknown Error")
                        }

                    }
                } else {
                    _liveDoctorsState.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    Log.d(TAG, "getDoctors: Result is not succesfull ${result}")
                    toast.postValue("Result is not succesfull")
                }

            } catch (e: Exception) {
                _liveDoctorsState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Log.d(TAG, "getDoctors: Exception tashavatti")
            }

        }
    }
}
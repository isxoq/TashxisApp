package uz.tashxis.client.framework.viewModel

import android.app.Application
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.data.BaseDomen
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.framework.repo.QueryRepository
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor.GetNearDoctorsRequest
import uz.tashxis.client.presentation.ui.bottom_nav.bosh_oyna.model.neardoctor.GetNearDoctorsRes

interface IGetNearDoctorsVM {
    val _liveState: LiveData<NetworkStatus<List<GetNearDoctorsRes>>>
    val liveState: LiveData<NetworkStatus<List<GetNearDoctorsRes>>>
    val _locationState: LiveData<NetworkStatus<Location>>
    val locationState: LiveData<NetworkStatus<Location>>
    fun getNearDoctors()
    fun getQueue()
}

class GetNearDoctorsVM(
    private val repository: MainRepository,
    application: Application
) :
    AndroidViewModel(application), IGetNearDoctorsVM {
    override val _liveState = MutableLiveData<NetworkStatus<List<GetNearDoctorsRes>>>()


    override val liveState: LiveData<NetworkStatus<List<GetNearDoctorsRes>>>
        get() = _liveState
    override val _locationState = MutableLiveData<NetworkStatus<Location>>()
    override val locationState: LiveData<NetworkStatus<Location>>
        get() = _locationState


    override fun getNearDoctors() {
        viewModelScope.launch {
            try {
                _liveState.postValue(NetworkStatus.LOADING())
                val result = repository.getNearDoctors(
                    GetNearDoctorsRequest(
                        40.07,
                        40.07,
                        10000
                    )
                )
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (body.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                Toast.makeText(
                                    uz.tashxis.client.App.context,
                                    "Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                                _liveState.postValue(NetworkStatus.SUCCESS(data))
                            } else {
                                _liveState.postValue(NetworkStatus.ERROR("data=null"))
                                Toast.makeText(
                                    uz.tashxis.client.App.context,
                                    "Data is null",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            Toast.makeText(
                                uz.tashxis.client.App.context,
                                "Unknown Error",
                                Toast.LENGTH_SHORT
                            ).show()
                            _liveState.postValue(NetworkStatus.ERROR("UnknownError"))
                        }
                    }

                }
            } catch (e: Exception) {
                Toast.makeText(uz.tashxis.client.App.context, "${e.message}", Toast.LENGTH_SHORT)
                    .show()
                _liveState.postValue(NetworkStatus.ERROR(e.message.toString()))
            }
        }
    }

    override fun getQueue() {

    }


}
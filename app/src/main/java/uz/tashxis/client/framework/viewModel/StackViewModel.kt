package uz.tashxis.client.framework.viewModel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.tashxis.client.business.util.NetworkStatus
import uz.tashxis.client.business.util.SingleLiveEvent
import uz.tashxis.client.data.BaseDomen
import uz.tashxis.client.framework.repo.MainRepository
import uz.tashxis.client.presentation.ui.auth.preference.PrefHelper
import uz.tashxis.client.presentation.ui.auth.preference.TashxisPrefs
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueRequest
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.AddQueueResLocal
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.StackDaysData
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.mapper.AddQueueResMapperImpl
import uz.tashxis.client.presentation.ui.bottom_nav.shifokor_oyna.model.stack.mapper.IAddQueueResMapper

class StackViewModel(
    private val authRepository: MainRepository,
    app: Application

) : AndroidViewModel(app) {
    private val addQueueResMapper: IAddQueueResMapper by lazy { AddQueueResMapperImpl() }
    private val prefs: TashxisPrefs by lazy { PrefHelper.getPref(app) }
    private val _liveStackDayState = MediatorLiveData<NetworkStatus<List<StackDaysData>>>()
    private val _liveStackTimeState = MediatorLiveData<NetworkStatus<List<String>>>()
    val liveStackDayState: LiveData<NetworkStatus<List<StackDaysData>>> =
        _liveStackDayState
    val livestackTimeState: LiveData<NetworkStatus<List<String>>> =
        _liveStackTimeState
    val toast = SingleLiveEvent<String>()
    val TAG = "TAG"
    var date = ""
    var time = ""

    fun getStackDays(id: Int) {
        viewModelScope.launch {
            try {
                Timber.d("getDoctorStackDays:Loading ")
                _liveStackDayState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getAcceptDays(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveStackDayState.postValue(NetworkStatus.SUCCESS(data))
                                Timber.d("getDoctorStackDays: Success")
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
                    Timber.d("getDoctorStackDays: Result is not succesfull $result")
                }

            } catch (e: Exception) {
                _liveStackDayState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Timber.d("getDoctorStackDays: Exception tashavatti")
            }

        }
    }

    fun getStackTimes(id: Int) {
        viewModelScope.launch {
            try {
                Timber.d("getDoctorStackTimes:Loading ")
                _liveStackTimeState.postValue(NetworkStatus.LOADING())
                val result = authRepository.getAcceptTimes(id)
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (result.body()!!.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveStackTimeState.postValue(NetworkStatus.SUCCESS(data))
                                Timber.d("getDoctorStackTimes: Success $data")
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
                    Timber.d("getDoctorStackTimes: Result is not succesfull $result")
                    toast.postValue("Result is not succesfull")
                }

            } catch (e: Exception) {
                _liveStackTimeState.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
                Timber.d("getDoctorStackTimes: Exception tashavatti")
            }

        }
    }

    private val _liveStackCommit = MutableLiveData<NetworkStatus<AddQueueResLocal>>()
    val liveStackCommit: LiveData<NetworkStatus<AddQueueResLocal>> = _liveStackCommit

    fun stackCommit(id: Int, price: Int) {
        viewModelScope.launch {
            try {
                _liveStackCommit.postValue(NetworkStatus.LOADING())
                val result = authRepository.stackCommit(
                    prefs.token ?: "",
                    AddQueueRequest(
                        doctorId = id,
                        type = 1,
                        price = price,
                        isPayed = 0,
                        date = date,
                        time = time
                    )
                )
                if (result.isSuccessful && result.body() != null) {
                    val body = result.body()!!
                    val data = body.data
                    when (body.code) {
                        BaseDomen.SUCCESS -> {
                            if (data != null) {
                                _liveStackCommit.postValue(
                                    NetworkStatus.SUCCESS(
                                        addQueueResMapper.map(
                                            data
                                        )
                                    )
                                )

                            } else {
                                _liveStackCommit.postValue(NetworkStatus.ERROR("data = null"))
                                toast.postValue("Data is null")
                            }
                        }
                        BaseDomen.UNKNOWN_ERROR -> {
                            _liveStackCommit.postValue(NetworkStatus.ERROR("UnknownError"))
                            toast.postValue("Unknown Error")
                        }

                    }
                } else {
                    _liveStackCommit.postValue(NetworkStatus.ERROR(" Result is not successful"))
                    Timber.d("stackCommit: Unknown Error " + result.body()!!.code)
                }
            } catch (e: Exception) {
                _liveStackCommit.postValue(NetworkStatus.ERROR(e.message ?: ""))
                toast.postValue(e.message)
            }
        }
    }
}
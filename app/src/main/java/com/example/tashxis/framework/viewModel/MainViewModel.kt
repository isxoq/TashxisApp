package com.example.tashxis.framework.viewModel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tashxis.Application
import com.example.tashxis.business.util.SingleLiveEvent
import com.example.tashxis.business.util.Status
import com.example.tashxis.framework.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


interface IMainViewModel{
    val liveState: LiveData<Status>
    val toast: LiveData<String>
    fun getSpeciality()
}

class MainViewModel(
    private val authRepository: MainRepository,
    application: Application

) : IMainViewModel,
    AndroidViewModel(application) {
    override val liveState=  SingleLiveEvent<Status>()
    override val toast = SingleLiveEvent<String>()

    override fun getSpeciality() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

            }
            catch (e: Exception)
            {

            }
        }
    }
}
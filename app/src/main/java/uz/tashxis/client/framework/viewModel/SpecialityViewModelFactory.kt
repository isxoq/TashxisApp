package uz.tashxis.client.framework.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.tashxis.client.framework.repo.MainRepository

class SpecialityViewModelFactory(val app: Application, private val repository: MainRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpecialityViewModel::class.java)) {
            return SpecialityViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
package com.example.tashxis.framework.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.framework.repo.MainRepository

class DoctorViewModelFactory(val app: Application, private val repository: MainRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorViewModel::class.java)) {
            return DoctorViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
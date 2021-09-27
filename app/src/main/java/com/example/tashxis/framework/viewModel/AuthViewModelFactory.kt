package com.example.tashxis.framework.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.framework.repo.AuthRepository

class AuthViewModelFactory(val app: Application, private val repository: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
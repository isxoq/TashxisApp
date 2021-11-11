package com.example.tashxis.framework.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tashxis.framework.repo.MainRepository
import com.example.tashxis.framework.viewmodel.StackViewModel

class StackViewModelFactory(val app: Application, private val repository: MainRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StackViewModel::class.java)) {
            return StackViewModel(repository, app) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
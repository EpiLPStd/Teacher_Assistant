package com.example.asystentnauczyciela.subjectgroup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asystentnauczyciela.select.SelectViewModel
import java.lang.IllegalArgumentException

class SubjectGroupViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectGroupViewModel::class.java)) {
            return SubjectGroupViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}
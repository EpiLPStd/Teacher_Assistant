package com.example.asystentnauczyciela.subjectstudent

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class StudentSubjectViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentSubjectViewModel::class.java)) {
            return StudentSubjectViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}
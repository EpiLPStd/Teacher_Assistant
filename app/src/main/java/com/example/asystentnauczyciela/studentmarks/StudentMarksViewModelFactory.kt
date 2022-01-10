package com.example.asystentnauczyciela.studentmarks

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class StudentMarksViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentMarksViewModel::class.java)) {
            return StudentMarksViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class.")
    }
}
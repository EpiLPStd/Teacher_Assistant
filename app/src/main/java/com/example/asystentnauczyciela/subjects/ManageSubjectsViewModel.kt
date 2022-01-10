package com.example.asystentnauczyciela.subjects

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManageSubjectsViewModel(application: Application): AndroidViewModel(application) {
    private val subjectsDAO: SubjectsDAO = TeacherDatabase.getInstance(application).subjectsDAO
    private val _subjectName = MutableLiveData<String>()
    private val _error = MutableLiveData<Boolean>()

    val subjectName: LiveData<String> get() = _subjectName
    val error: LiveData<Boolean> get() = _error

    init {
        _subjectName.value = ""
        _error.value = false
    }

    private fun updateName(value: String){ _subjectName.value = value }
    fun setError() { _error.value = false }

    fun checkSubject(valueName: String) {
        if (!subjectsDAO.isAlready(valueName)) {
            updateName(valueName)
        }
        else {
            Toast.makeText(getApplication(), "Przedmiot znajduje się już w bazie", Toast.LENGTH_SHORT).show()
            _error.value = true
        }
    }

    fun addSubject(subject: Subject) {
        viewModelScope.launch(Dispatchers.IO) { subjectsDAO.insertSubject(subject) }
    }
}
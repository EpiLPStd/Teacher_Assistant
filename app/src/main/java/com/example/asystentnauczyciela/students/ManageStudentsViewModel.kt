package com.example.asystentnauczyciela.students

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManageStudentsViewModel(application: Application): AndroidViewModel(application) {
    private val studentsDAO: StudentsDAO = TeacherDatabase.getInstance(application).studentsDAO
    private val _studentName = MutableLiveData<String>()
    private val _studentID = MutableLiveData<String>()
    private val _error = MutableLiveData<Boolean>()

    val studentName: LiveData<String> get() = _studentName
    val studentID: LiveData<String> get() = _studentID
    val error: LiveData<Boolean> get() = _error

    init {
        _studentName.value = ""
        _studentID.value = ""
        _error.value = false
    }

    private fun updateName(value: String){ _studentName.value = value }
    private fun updateID(value: String){ _studentID.value = value }
    fun setError() { _error.value = false }

    fun checkStudent(valueName: String, valueID: String) {
        if (!studentsDAO.isAlready(valueID)) {
            updateName(valueName)

            if (valueID.length == 6 && !(valueID.contains(',')) && !(valueID.contains('.')) && !(valueID.contains(' ')) && !(valueID.contains('-'))) {
                updateID(valueID)
            }
            else {
                Toast.makeText(getApplication(), "Numer albumu powinien składać się z sześciu cyfr", Toast.LENGTH_SHORT).show()
                _error.value = true
            }
        }
        else {
            Toast.makeText(getApplication(), "Student o takim numerze albumu znajduje się już w bazie", Toast.LENGTH_SHORT).show()
            _error.value = true
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch(Dispatchers.IO) { studentsDAO.insertStudent(student) }
    }
}
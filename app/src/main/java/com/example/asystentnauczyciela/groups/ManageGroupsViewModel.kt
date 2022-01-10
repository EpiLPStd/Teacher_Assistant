package com.example.asystentnauczyciela.groups

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.Group
import com.example.asystentnauczyciela.subjects.SubjectsDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ManageGroupsViewModel(application: Application): AndroidViewModel(application) {
    private val subjectsDAO: SubjectsDAO = TeacherDatabase.getInstance(application).subjectsDAO
    private val groupsDAO: GroupsDAO = TeacherDatabase.getInstance(application).groupsDAO
    val subjects: List<String> = subjectsDAO.getSubjectsList()

    private val _groupName = MutableLiveData<String>()
    private val _groupDayOfWeek = MutableLiveData<String>()
    private val _groupStartHour = MutableLiveData<String>()
    private val _groupEndHour = MutableLiveData<String>()
    private val _error = MutableLiveData<Boolean>()

    val groupName: LiveData<String> get() = _groupName
    val groupDayOfWeek: LiveData<String> get() = _groupDayOfWeek
    val groupStartHour: LiveData<String> get() = _groupStartHour
    val groupEndHour: LiveData<String> get() = _groupEndHour
    val error: LiveData<Boolean> get() = _error

    init {
        _groupName.value = ""
        _groupDayOfWeek.value = ""
        _groupStartHour.value = ""
        _groupEndHour.value = ""
        _error.value = false
    }

    private fun updateName(value: String) { _groupName.value = value }
    private fun updateDayOfWeek(value: String) { _groupDayOfWeek.value = value }
    private fun updateStartHour(value: String) { _groupStartHour.value = value }
    private fun updateEndHour(value: String) { _groupEndHour.value = value }
    fun setError() { _error.value = false }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkGroupName(valueSubject: String, valueGroup: String, valueDay: String, valueStart: String, valueEnd: String) {
        if (!groupsDAO.isAlready(valueSubject, valueGroup)) {
            updateName(valueGroup)
            updateDayOfWeek(valueDay)

            if (!valueStart.contains(".") && !valueStart.contains("-") && !valueStart.contains(" ") && valueStart.length >= 4) {
                val startH = valueStart.split(":")[0]
                val startMin = valueStart.split(":")[1]

                if (startH.length <= 2 || startMin.length <= 2) {
                    if (startH.toInt() < 24 && startMin.toInt() < 60) {
                        updateStartHour(valueStart)
                    }
                    else {
                        Toast.makeText(getApplication(), "Nieprawidłowy format godziny rozpoczęcia zajęć", Toast.LENGTH_SHORT).show()
                        _error.value = true
                    }
                }
                else {
                    Toast.makeText(getApplication(), "Nieprawidłowy format godziny rozpoczęcia zajęć", Toast.LENGTH_SHORT).show()
                    _error.value = true
                }

                if (!valueEnd.contains(".") && !valueEnd.contains("-") && !valueEnd.contains(" ") && valueEnd.length >= 4) {
                    val endH = valueEnd.split(":")[0]
                    val endMin = valueEnd.split(":")[1]

                    if (endH.length <= 2 || endMin.length <= 2) {
                        if (endH.toInt() < 24 && endMin.toInt() < 60) {
                            if (_groupStartHour.value != "") {
                                val startTime = LocalTime.parse(_groupStartHour.value, DateTimeFormatter.ofPattern("H:m"))
                                val endTime = LocalTime.parse(valueEnd, DateTimeFormatter.ofPattern("H:m"))

                                if (startTime.isBefore(endTime)) {
                                    updateEndHour(valueEnd)
                                }
                                else {
                                    Toast.makeText(getApplication(), "Godzina zakończenia nie może być przed godziną rozpoczęcia", Toast.LENGTH_SHORT).show()
                                    _error.value = true
                                }
                            }
                        }
                        else {
                            Toast.makeText(getApplication(), "Nieprawidłowy format godziny zakończenia zajęć", Toast.LENGTH_SHORT).show()
                            _error.value = true
                        }
                    }
                    else {
                        Toast.makeText(getApplication(), "Nieprawidłowy format godziny zakończenia zajęć", Toast.LENGTH_SHORT).show()
                        _error.value = true
                    }
                }
                else {
                    Toast.makeText(getApplication(), "Nieprawidłowy format godziny zakończenia zajęć", Toast.LENGTH_SHORT).show()
                    _error.value = true
                }
            }
            else {
                Toast.makeText(getApplication(), "Nieprawidłowy format godziny rozpoczęcia zajęć", Toast.LENGTH_SHORT).show()
                _error.value = true
            }
        }
        else {
            Toast.makeText(getApplication(), "Podana grupa istnieje już dla wybranego przedmiotu", Toast.LENGTH_SHORT).show()
            _error.value = true
        }
    }

    fun addGroup(group: Group) {
        viewModelScope.launch(Dispatchers.IO) { groupsDAO.insertGroup(group) }
    }
}
package com.example.asystentnauczyciela.addmark

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.Mark
import com.example.asystentnauczyciela.studentmarks.MarksDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMarkViewModel(application: Application): AndroidViewModel(application) {
    private val marksDAO: MarksDAO = TeacherDatabase.getInstance(application).marksDAO
    private val _markType = MutableLiveData<String>()
    private val _markValue = MutableLiveData<String>()
    private val _markDescription = MutableLiveData<String>()
    private val _error = MutableLiveData<Boolean>()

    val markType: LiveData<String> get() =_markType
    val markValue: LiveData<String> get() = _markValue
    val markDescription: LiveData<String> get() = _markDescription
    val error: LiveData<Boolean> get() = _error

    init {
        _markType.value = ""
        _markValue.value = ""
        _markDescription.value = ""
        _error.value = false
    }

    fun setError() { _error.value = false }
    private fun updateValues(valueMark: String, valueType: String, valueDescription: String) {
        _markType.value = valueMark
        _markValue.value = valueType
        _markDescription.value = valueDescription
    }

    fun checkValue(valueMark: String, valueType: String, valueDescription: String) {
        if (!(valueMark.contains(',')) && !(valueMark.contains(' ')) && !(valueMark.contains('-'))) {
            if (valueType == "ocena") {
                val possibleMarks = listOf("2", "2.5", "3", "3.5", "4", "4.5", "5")

                if (valueMark in possibleMarks) {
                    updateValues(valueMark, valueType, valueDescription)
                }
                else {
                    Toast.makeText(getApplication(), "Ocena powinna być z zakresu 2 - 5", Toast.LENGTH_SHORT).show()
                    _error.value = true
                }
            }
            else if (valueType == "punkty") {
                updateValues(valueMark, valueType, valueDescription)
            }
        }
        else {
            Toast.makeText(getApplication(), "Ocena wprowadzona w niewłaściwym formacie", Toast.LENGTH_SHORT).show()
            _error.value = true
        }
    }

    fun addMark(mark: Mark) {
        viewModelScope.launch(Dispatchers.IO) { marksDAO.insertMark(mark) }
    }
}
package com.example.asystentnauczyciela.studentclass

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.StudentsGroups
import com.example.asystentnauczyciela.groups.GroupsDAO
import com.example.asystentnauczyciela.students.StudentsDAO
import com.example.asystentnauczyciela.subjects.SubjectsDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentClassViewModel(application: Application): AndroidViewModel(application) {
    private val subjectsDAO: SubjectsDAO = TeacherDatabase.getInstance(application).subjectsDAO
    private val groupsDAO: GroupsDAO = TeacherDatabase.getInstance(application).groupsDAO
    private val studentsDAO: StudentsDAO = TeacherDatabase.getInstance(application).studentsDAO
    private val studentsGroupsDAO: StudentsGroupsDAO = TeacherDatabase.getInstance(application).studentsGroupsDAO

    val subjects = subjectsDAO.getSubjectsList()
    var students = mutableListOf<String>()
    val studentsNames = studentsDAO.getStudentsList()
    val albums = studentsDAO.getStudentsAlbumsList()

    private val _subjectName = MutableLiveData<String>()
    private val _groupName = MutableLiveData<String>()
    private val _studentName = MutableLiveData<String>()
    private val _error = MutableLiveData<Boolean>()

    val subjectName: LiveData<String> get() = _subjectName
    val groupName: LiveData<String> get() = _groupName
    val studentName: LiveData<String> get() = _studentName
    val error: LiveData<Boolean> get() = _error

    init {
        _subjectName.value = ""
        _groupName.value = ""
        _studentName.value = ""
        _error.value = false
    }

    fun groupsInit(valueSubject: String): List<String> {
        return groupsDAO.getGroupsList(valueSubject)
    }

    fun studentsInit() {
        val tempStudents = mutableListOf<StudentRow>()

        for (i in studentsNames.indices) {
            tempStudents.add(StudentRow(studentsNames[i], albums[i]))
        }

        tempStudents.sortWith { row1: StudentRow, row2: StudentRow -> row1.album.toInt() - row2.album.toInt() }

        tempStudents.forEach {
            students.add(it.name + ", " + it.album)
        }
    }

    data class StudentRow(val name: String, val album: String)

    private fun updateRow(valueSubject: String, valueGroup: String, valueStudent: String) {
        _subjectName.value = valueSubject
        _groupName.value = valueGroup
        _studentName.value = valueStudent
    }

    fun setError() { _error.value = false }

    fun getStudentID(valueStudent: String): Long {
        return studentsDAO.getID(valueStudent)
    }

    fun getGroupID(valueSubject: String, valueGroup: String): Long {
        return groupsDAO.getID(valueSubject, valueGroup)
    }

    fun checkStudentClass(valueSubject: String, valueGroup: String, valueStudent: String) {
        val studentID = getStudentID(valueStudent.takeLast(6))

        if (!studentsGroupsDAO.isAlready(valueSubject, studentID)) {
            updateRow(valueSubject, valueGroup, valueStudent.take(valueStudent.length - 8))
        }
        else {
            Toast.makeText(getApplication(), "Student znajduje się już na liście przedmiotu", Toast.LENGTH_SHORT).show()
            _error.value = true
        }
    }

    fun addStudentClass(studentsGroups: StudentsGroups) {
        viewModelScope.launch(Dispatchers.IO) { studentsGroupsDAO.insertStudentGroups(studentsGroups) }
    }
}
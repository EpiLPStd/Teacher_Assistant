package com.example.asystentnauczyciela.subjectstudent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.GroupsWithStudents
import com.example.asystentnauczyciela.groups.GroupsDAO
import com.example.asystentnauczyciela.studentclass.StudentsGroupsDAO

class StudentSubjectViewModel(application: Application): AndroidViewModel(application) {
    private val studentsGroupsDAO: StudentsGroupsDAO = TeacherDatabase.getInstance(application).studentsGroupsDAO
    private val groupsDAO: GroupsDAO = TeacherDatabase.getInstance(application).groupsDAO

    private fun getGroupID(valueSubject: String, valueGroup: String): Long {
        return groupsDAO.getID(valueSubject, valueGroup)
    }

    fun getStudents(valueSubject: String, valueGroup: String): LiveData<List<GroupsWithStudents>> {
        return studentsGroupsDAO.getStudentGroups(getGroupID(valueSubject, valueGroup))
    }
}
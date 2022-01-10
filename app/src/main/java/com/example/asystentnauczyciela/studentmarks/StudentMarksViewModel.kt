package com.example.asystentnauczyciela.studentmarks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.Mark
import com.example.asystentnauczyciela.groups.GroupsDAO

class StudentMarksViewModel(application: Application): AndroidViewModel(application) {
    private val marksDAO: MarksDAO = TeacherDatabase.getInstance(application).marksDAO
    private val groupsDAO: GroupsDAO = TeacherDatabase.getInstance(application).groupsDAO

    fun getGroupId(valueSubject: String, valueGroup: String): Long {
        return groupsDAO.getID(valueSubject, valueGroup)
    }

    fun getStudentMarks(valueSubject: String, valueGroup: String, valueStudent: Long): LiveData<List<Mark>> {
        return marksDAO.getStudentSubjectMarks(valueSubject, valueStudent)
    }
}
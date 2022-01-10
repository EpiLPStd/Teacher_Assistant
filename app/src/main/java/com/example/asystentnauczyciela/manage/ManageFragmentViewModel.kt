package com.example.asystentnauczyciela.manage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.groups.GroupsDAO
import com.example.asystentnauczyciela.studentclass.StudentsGroupsDAO
import com.example.asystentnauczyciela.studentmarks.MarksDAO
import com.example.asystentnauczyciela.students.StudentsDAO
import com.example.asystentnauczyciela.subjects.SubjectsDAO

class ManageFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val groupsDAO: GroupsDAO = TeacherDatabase.getInstance(application).groupsDAO
    private val marksDAO: MarksDAO = TeacherDatabase.getInstance(application).marksDAO
    private val studentsDAO: StudentsDAO = TeacherDatabase.getInstance(application).studentsDAO
    private val studentsGroupsDAO: StudentsGroupsDAO = TeacherDatabase.getInstance(application).studentsGroupsDAO
    private val subjectsDAO: SubjectsDAO = TeacherDatabase.getInstance(application).subjectsDAO

    fun terminate() {
        groupsDAO.terminate()
        marksDAO.terminate()
        studentsDAO.terminate()
        studentsGroupsDAO.terminate()
        subjectsDAO.terminate()
    }
}
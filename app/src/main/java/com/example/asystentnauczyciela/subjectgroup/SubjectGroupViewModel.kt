package com.example.asystentnauczyciela.subjectgroup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.Group
import com.example.asystentnauczyciela.entities.SubjectGroups
import com.example.asystentnauczyciela.groups.GroupsDAO

class SubjectGroupViewModel(application: Application): AndroidViewModel(application) {
    private val groupsDAO: GroupsDAO = TeacherDatabase.getInstance(application).groupsDAO

    fun getSubjectGroups(value: String): LiveData<List<SubjectGroups>> {
        return groupsDAO.getSubjectGroups(value)
    }
}
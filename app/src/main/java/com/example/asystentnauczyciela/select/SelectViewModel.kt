package com.example.asystentnauczyciela.select

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.asystentnauczyciela.TeacherDatabase
import com.example.asystentnauczyciela.entities.Subject
import com.example.asystentnauczyciela.subjects.SubjectsDAO

class SelectViewModel(application: Application): AndroidViewModel(application) {
    private val subjectsDAO: SubjectsDAO = TeacherDatabase.getInstance(application).subjectsDAO
    val subjects: LiveData<List<Subject>> = subjectsDAO.getSubjects()
}
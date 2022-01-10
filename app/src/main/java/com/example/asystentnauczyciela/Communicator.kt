package com.example.asystentnauczyciela

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator : ViewModel(){

    val SubjectName = MutableLiveData<Any>()
    val GroupName = MutableLiveData<Any>()
    val StudentName = MutableLiveData<Any>()
    val StudentID = MutableLiveData<Any>()

    fun setSubjectName(value: String){
        SubjectName.value = value
    }

    fun setGroupName(value: String){
        GroupName.value = value
    }

    fun setStudentName(value: String){
        StudentName.value = value
    }

    fun setStudentID(value: String){
        StudentID.value = value
    }
}
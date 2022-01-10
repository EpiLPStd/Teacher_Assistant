package com.example.asystentnauczyciela.entities

import androidx.room.Embedded
import androidx.room.Relation

data class StudentsWithMarks (
    @Embedded val student: Student,

    @Relation(parentColumn = "student_id", entityColumn = "student_id")
    val marks: List<Mark>
)
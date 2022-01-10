package com.example.asystentnauczyciela.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GroupsWithStudents(
    @Embedded val group: Group,

    @Relation(
        parentColumn = "group_id",
        entityColumn = "student_id",
        associateBy = Junction(StudentsGroups::class)
    ) val students: List<Student>
)
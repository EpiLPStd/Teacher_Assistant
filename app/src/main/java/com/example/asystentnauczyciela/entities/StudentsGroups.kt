package com.example.asystentnauczyciela.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "students_groups_table",
    primaryKeys = ["group_id", "student_id"]
)
data class StudentsGroups (
    @ColumnInfo(name = "group_id")
    val groupID: Long,

    @ColumnInfo(name = "student_id")
    val studentID: Long
)
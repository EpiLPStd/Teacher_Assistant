package com.example.asystentnauczyciela.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_marks")
data class Mark (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var markID: Long,

    @ColumnInfo(name = "mark_value")
    var markValue: String? = null,

    @ColumnInfo(name = "mark_type")
    var markType: String? = null,

    @ColumnInfo(name = "student_id")
    var studentID: Long,

    @ColumnInfo(name = "subject_name")
    var subjectName: String? = null,

    @ColumnInfo(name = "mark_description")
    var markDescription: String? = null,
)
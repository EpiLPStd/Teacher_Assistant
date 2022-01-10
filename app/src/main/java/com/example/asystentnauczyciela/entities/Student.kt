package com.example.asystentnauczyciela.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class Student (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="student_id")
    var studentID: Long,

    @ColumnInfo(name = "name")
    var studentName: String? = null,

    @ColumnInfo(name = "album")
    var studentAlbum: String? = null,
)
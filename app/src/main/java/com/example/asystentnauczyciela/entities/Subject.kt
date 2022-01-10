package com.example.asystentnauczyciela.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects_table")
data class Subject (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var subjectID: Long,

    @ColumnInfo(name = "name")
    var subjectName: String
)
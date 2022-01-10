package com.example.asystentnauczyciela.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups_table")
data class Group (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "group_id")
    var groupID: Long,

    @ColumnInfo(name = "group_name")
    var groupName: String? = null,

    @ColumnInfo(name = "subject_name")
    var subjectName: String? = null,

    @ColumnInfo(name = "day_of_week")
    var groupDayOfWeek: String? = null,

    @ColumnInfo(name = "start_hour")
    var groupStart: String? = null,

    @ColumnInfo(name = "end_hour")
    var groupEnd: String? = null
)
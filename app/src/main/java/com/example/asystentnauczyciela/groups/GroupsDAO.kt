package com.example.asystentnauczyciela.groups

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.asystentnauczyciela.entities.Group
import com.example.asystentnauczyciela.entities.SubjectGroups

@Dao
interface GroupsDAO {
    @Insert
    fun insertGroup(group: Group)

    @Transaction
    @Query("SELECT * FROM subjects_table s INNER JOIN groups_table g ON s.name = g.subject_name AND s.name = :value")
    fun getSubjectGroups(value: String): LiveData<List<SubjectGroups>>

    @Query("SELECT group_name FROM groups_table WHERE subject_name = :value")
    fun getGroupsList(value: String): List<String>

    @Query("SELECT EXISTS (SELECT * FROM groups_table WHERE subject_name = :valueSubject AND group_name = :valueGroup)")
    fun isAlready(valueSubject: String, valueGroup: String): Boolean

    @Query("SELECT group_id FROM groups_table WHERE subject_name = :valueSubject AND group_name = :valueGroup")
    fun getID(valueSubject: String, valueGroup: String): Long

    @Query("DELETE FROM groups_table")
    fun terminate()
}
package com.example.asystentnauczyciela.studentclass

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asystentnauczyciela.entities.GroupsWithStudents
import com.example.asystentnauczyciela.entities.Student
import com.example.asystentnauczyciela.entities.StudentsGroups

@Dao
interface StudentsGroupsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentGroups(studentsGroups: StudentsGroups)

    @Transaction
    @Query("SELECT * FROM students_table s INNER JOIN students_groups_table sg ON s.student_id = sg.student_id WHERE sg.group_id = :value")
    fun getStudentGroups(value: Long): LiveData<List<GroupsWithStudents>>

    @Transaction
    @Query("SELECT EXISTS (SELECT * FROM students_groups_table s INNER JOIN groups_table g ON s.group_id = g.group_id WHERE s.student_id = :valueStudent AND g.subject_name = :valueSubject)")
    fun isAlready(valueSubject: String, valueStudent: Long): Boolean

    @Query("DELETE FROM students_groups_table")
    fun terminate()
}
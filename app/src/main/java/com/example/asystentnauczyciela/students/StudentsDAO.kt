package com.example.asystentnauczyciela.students

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.asystentnauczyciela.entities.Student

@Dao
interface StudentsDAO {
    @Insert
    fun insertStudent(student: Student)

    @Query("SELECT name FROM students_table")
    fun getStudentsList(): List<String>

    @Query("SELECT album FROM students_table")
    fun getStudentsAlbumsList(): List<String>

    @Query("SELECT EXISTS (SELECT * FROM students_table WHERE album = :value)")
    fun isAlready(value: String): Boolean

    @Query("SELECT student_id FROM students_table WHERE album = :value")
    fun getID(value: String): Long

    @Query("DELETE FROM students_table")
    fun terminate()
}
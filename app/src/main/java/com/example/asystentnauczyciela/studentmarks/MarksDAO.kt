package com.example.asystentnauczyciela.studentmarks

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asystentnauczyciela.entities.Mark

@Dao
interface MarksDAO {
    @Insert
    fun insertMark(mark: Mark)

    @Transaction
    @Query("SELECT * FROM students_marks m INNER JOIN students_table s ON m.student_id = s.student_id WHERE m.student_id = :valueStudent AND m.subject_name = :valueSubject")
    fun getStudentSubjectMarks(valueSubject: String, valueStudent: Long): LiveData<List<Mark>>

    @Query("DELETE FROM students_marks")
    fun terminate()
}
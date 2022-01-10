package com.example.asystentnauczyciela.subjects

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.asystentnauczyciela.entities.Subject

@Dao
interface SubjectsDAO {
    @Insert
    fun insertSubject(subject: Subject)

    @Query("SELECT * FROM subjects_table")
    fun getSubjects(): LiveData<List<Subject>>

    @Query("SELECT name FROM subjects_table")
    fun getSubjectsList(): List<String>

    @Query("SELECT EXISTS (SELECT * FROM subjects_table WHERE name = :value)")
    fun isAlready(value: String): Boolean

    @Query("DELETE FROM subjects_table")
    fun terminate()
}
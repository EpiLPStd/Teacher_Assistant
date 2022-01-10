package com.example.asystentnauczyciela

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asystentnauczyciela.entities.*
import com.example.asystentnauczyciela.groups.GroupsDAO
import com.example.asystentnauczyciela.studentclass.StudentsGroupsDAO
import com.example.asystentnauczyciela.studentmarks.MarksDAO
import com.example.asystentnauczyciela.students.StudentsDAO
import com.example.asystentnauczyciela.subjects.SubjectsDAO

@Database(entities = [Subject::class, Student::class, Group::class, Mark::class, StudentsGroups::class], version = 1, exportSchema = false)
abstract class TeacherDatabase: RoomDatabase() {
    abstract val subjectsDAO: SubjectsDAO
    abstract val studentsDAO: StudentsDAO
    abstract val groupsDAO: GroupsDAO
    abstract val studentsGroupsDAO: StudentsGroupsDAO
    abstract val marksDAO: MarksDAO

    companion object {
        @Volatile
        private var INSTANCE: TeacherDatabase? = null

        fun getInstance(context: Context): TeacherDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                TeacherDatabase::class.java, "teacher_database").
                        fallbackToDestructiveMigration().allowMainThreadQueries().build()

                INSTANCE = instance
            }

            return instance
        }
    }
}
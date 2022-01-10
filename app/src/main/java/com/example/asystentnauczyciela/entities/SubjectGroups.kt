package com.example.asystentnauczyciela.entities

import androidx.room.Embedded
import androidx.room.Relation

data class SubjectGroups (
    @Embedded val subject: Subject,

    @Relation(parentColumn = "name", entityColumn = "subject_name")
    val groups: List<Group>
)
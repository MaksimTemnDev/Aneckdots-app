package com.example.anekdots.Room.Entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aneckdot_table")
data class Aneckdot(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val text: String,
    val link: String
)

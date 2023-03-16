package com.example.anekdots.Room.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.anekdots.Room.Entites.Aneckdot

@Dao
interface AneckdotDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAneckdot(aneckdot: Aneckdot)

    @Query("SELECT * FROM aneckdot_table ORDER BY id")
    fun readAll(): LiveData<List<Aneckdot>>

    @Delete
    fun deleteAneckdot(aneckdot: Aneckdot)

    @Query("DELETE FROM aneckdot_table WHERE text = :txt")
    fun deletBytext(txt:String)
}
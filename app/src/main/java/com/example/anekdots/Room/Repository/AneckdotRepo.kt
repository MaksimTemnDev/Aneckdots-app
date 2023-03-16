package com.example.anekdots.Room.Repository

import androidx.lifecycle.LiveData
import com.example.anekdots.Room.DAO.AneckdotDAO
import com.example.anekdots.Room.Entites.Aneckdot

class AneckdotRepo(private val aneckdotDAO: AneckdotDAO) {

    val readAllData: LiveData<List<Aneckdot>> = aneckdotDAO.readAll()

    suspend fun addAneckdot(aneckdot: Aneckdot){
        aneckdotDAO.addAneckdot(aneckdot)
    }
}
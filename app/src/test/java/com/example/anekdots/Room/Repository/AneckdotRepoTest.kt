package com.example.anekdots.Room.Repository

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.anekdots.Room.DAO.AneckdotDAO
import com.example.anekdots.Room.Entites.Aneckdot
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AneckdotRepoTest: TestCase() {

    private lateinit var db: Database
    private lateinit var dao: AneckdotDAO
     @Before
     public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
         db = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
         dao = db.aneckdotDao()
     }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun getReadAllData() = runBlocking {
        val input = Aneckdot(0,"Kotlin", "The Best")
        dao.addAneckdot(input)
        val res = dao.readAll()
        assert(res.value!!.contains(input))
    }

    @Test
    fun addAneckdot() {
    }
}
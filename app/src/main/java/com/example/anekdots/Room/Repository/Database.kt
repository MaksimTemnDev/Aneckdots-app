package com.example.anekdots.Room.Repository

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anekdots.Room.DAO.AneckdotDAO
import com.example.anekdots.Room.Entites.Aneckdot

@androidx.room.Database(entities = [Aneckdot::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun aneckdotDao(): AneckdotDAO
    companion object{
//        @Volatile
//        private var INSTANCE: Database? = null

        fun getDatabase(context: Context): Database{
            return Room.databaseBuilder(
                context.applicationContext,
                Database::class.java,
                "database"
            ).build()
        }

//        fun getDatabase(context: Context): Database{
//            val tempInstance = INSTANCE
//            if(tempInstance!= null){
//                return tempInstance
//            }
//            synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    Database::class.java,
//                    "aneckdots_database")
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//        }
    }
}
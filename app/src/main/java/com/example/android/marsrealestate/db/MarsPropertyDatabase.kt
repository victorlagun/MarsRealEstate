package com.example.android.marsrealestate.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MarsProperty::class],
    version = 1
)
abstract class MarsPropertiesDatabase : RoomDatabase() {

    companion object {
        private val INSTANCE: MarsPropertiesDatabase? = null

        fun getInstance(context: Context): MarsPropertiesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    MarsPropertiesDatabase::class.java,
                    "mars_db"
                ).build()
            }
    }

    abstract fun dao(): MarsPropertyDAO
}

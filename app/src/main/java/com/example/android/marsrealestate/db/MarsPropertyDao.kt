package com.example.android.marsrealestate.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MarsPropertyDAO {
    @Query("SELECT * FROM mars_property WHERE id=:id")
    fun get(id: String): Flow<MarsProperty>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(property: List<MarsProperty>)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(property: MarsProperty)
    @Delete
    fun delete(property: MarsProperty)
    @Query("SELECT * FROM mars_property")
    fun getAll(): Flow<List<MarsProperty>>
}
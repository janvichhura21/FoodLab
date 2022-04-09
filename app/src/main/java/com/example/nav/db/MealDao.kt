package com.example.nav.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nav.model.MealX

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertmeal(mealX: MealX)

    @Delete
    suspend fun deletemeal(mealX: MealX)

    @Query("SELECT * FROM mealtableinfo")
    fun getmeallistlivedata():LiveData<List<MealX>>
}
package com.example.nav.db

import android.content.Context
import androidx.room.*
import com.example.nav.model.MealX

@Database(entities = [MealX::class], version = 1)
@TypeConverters(MealTypeConvertors::class)
abstract class MealDatabase:RoomDatabase() {

    abstract fun mealDao():MealDao

    companion object{
        @Volatile
        private var INSTANCE:MealDatabase?=null

        @Synchronized
        fun getbdinstance(context: Context):MealDatabase{
            if (INSTANCE==null){
                INSTANCE=Room.databaseBuilder(context,MealDatabase::class.java,"mealname")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}
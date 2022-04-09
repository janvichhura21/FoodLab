package com.example.nav.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertors {

    @TypeConverter
    fun getString(attributes:Any?):String{
        if (attributes==null){
            return ""
        }
        return attributes as String
    }
    @TypeConverter
    fun getAny(attributes:String):Any{
        if (attributes==null){
            return ""
        }
        return attributes
    }
}
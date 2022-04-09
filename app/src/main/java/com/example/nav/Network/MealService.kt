package com.example.nav.Network

import com.example.nav.model.CategoriList
import com.example.nav.model.Meal
import com.example.nav.model.MealCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL="https://www.themealdb.com/api/json/v1/1/"
interface MealService {

    @GET("random.php")
     fun getmeal(): Call<Meal>

     @GET("lookup.php?")
     fun getmealdetails(@Query("i")id:String):Call<Meal>

     @GET("filter.php?")
     fun getmealcategory(@Query("c")categoryName:String):Call<MealCategory>

     @GET("categories.php")
     fun getcategory():Call<CategoriList>

     @GET("filter.php")
     fun getmealcategorylist(@Query("c")categoryName:String):Call<MealCategory>
}
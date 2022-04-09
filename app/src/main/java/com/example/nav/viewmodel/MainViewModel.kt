package com.example.nav.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav.Network.RetrofitInstance
import com.example.nav.db.MealDatabase
import com.example.nav.model.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val mealDatabase: MealDatabase):ViewModel() {

    var api= MutableLiveData<MealX>()
    val apilist=MutableLiveData<List<MealXX>>()
    val categoryapi=MutableLiveData<List<Category>>()
    var mealdatabseapi=mealDatabase.mealDao().getmeallistlivedata()
    fun getapi() {
        RetrofitInstance.getInstance.getmeal().enqueue(object :Callback<Meal>{
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.body()!=null){
                    val randommeal:MealX=response.body()!!.meals[0]
                    api.value=randommeal
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getmealapi(){
        RetrofitInstance.getInstance.getmealcategory("Seafood").enqueue(object :Callback<MealCategory>{
            override fun onResponse(call: Call<MealCategory>, response: Response<MealCategory>) {
                if (response.body()!=null){
                    apilist.value= response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealCategory>, t: Throwable) {
               Log.d("task","error",t)
            }

        })
    }

    fun getcateorymealfood(){
        RetrofitInstance.getInstance.getcategory().enqueue(object :Callback<CategoriList>{
            override fun onResponse(call: Call<CategoriList>, response: Response<CategoriList>) {
                response.body()?.let { categorylist->
                    categoryapi.postValue(categorylist.categories)
                }
            }

            override fun onFailure(call: Call<CategoriList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
 fun getfood():LiveData<MealX>{
     return api
 }
    fun getcateoryfood(): LiveData<List<MealXX>> {
        return apilist
    }

    fun getcategoryLiveData():LiveData<List<Category>>{
        return categoryapi
    }

    fun observefavmealdblivedata(): LiveData<List<MealX>> {
        return mealdatabseapi
    }
    fun insertmeal(mealX: MealX)=viewModelScope.launch {
        mealDatabase.mealDao().insertmeal(mealX)
    }
    fun delete(mealX: MealX)=viewModelScope.launch {
        mealDatabase.mealDao().deletemeal(mealX)
    }

}
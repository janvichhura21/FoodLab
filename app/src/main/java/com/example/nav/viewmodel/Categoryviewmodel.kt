package com.example.nav.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nav.Network.RetrofitInstance
import com.example.nav.model.MealCategory
import com.example.nav.model.MealXX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Categoryviewmodel:ViewModel() {

    var categorydata=MutableLiveData<List<MealXX>>()
    fun getmealcategorylist(categoryName:String){
        RetrofitInstance.getInstance.getmealcategorylist(categoryName).enqueue(object :Callback<MealCategory>{
            override fun onResponse(call: Call<MealCategory>, response: Response<MealCategory>) {
                response.body()?.let { category->
                    categorydata.postValue(category.meals)
                }
            }

            override fun onFailure(call: Call<MealCategory>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun categorylivedata():LiveData<List<MealXX>>{
        return categorydata
    }
}
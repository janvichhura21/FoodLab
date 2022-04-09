package com.example.nav.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nav.Network.RetrofitInstance
import com.example.nav.db.MealDatabase
import com.example.nav.model.Meal
import com.example.nav.model.MealX
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailViewModel(val mealDatabase: MealDatabase):ViewModel() {

    var mealdetails=MutableLiveData<MealX>()

    fun getmealdetail(id:String){
        RetrofitInstance.getInstance.getmealdetails(id).enqueue(object :Callback<Meal>{
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                if (response.body()!=null){
                    mealdetails.value= response.body()!!.meals[0]
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
    fun getdetails():LiveData<MealX>{
        return mealdetails
    }

    fun insertmeal(mealX: MealX)=viewModelScope.launch {
        mealDatabase.mealDao().insertmeal(mealX)
    }
    fun delete(mealX: MealX)=viewModelScope.launch {
        mealDatabase.mealDao().deletemeal(mealX)
    }
}
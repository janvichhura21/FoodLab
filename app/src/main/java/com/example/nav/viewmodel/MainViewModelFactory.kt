package com.example.nav.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nav.db.MealDatabase

class MainViewModelFactory(private val mealDatabase: MealDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java!!)) {
            MainViewModel(mealDatabase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
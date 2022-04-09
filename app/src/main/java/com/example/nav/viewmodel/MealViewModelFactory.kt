package com.example.nav.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nav.db.MealDatabase

class MealViewModelFactory(private val mealDatabase: MealDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealDetailViewModel::class.java!!)) {
            MealDetailViewModel(mealDatabase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
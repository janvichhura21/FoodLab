package com.example.nav.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nav.HomeFragment
import com.example.nav.MealActivity
import com.example.nav.adapter.CategoryliistAdapter
import com.example.nav.databinding.ActivityCategoryBinding
import com.example.nav.databinding.FragmentCategoryBinding
import com.example.nav.viewmodel.Categoryviewmodel
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    lateinit var cVm:Categoryviewmodel
    lateinit var binding: ActivityCategoryBinding
    lateinit var categorylistAdapter:CategoryliistAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categorylistAdapter= CategoryliistAdapter()
        cVm= ViewModelProviders.of(this)[Categoryviewmodel::class.java]
        onitemsow()
        cVm.getmealcategorylist(intent.getStringExtra(HomeFragment.Categoryname)!!)
        setupmv()
        setuprv()
        onitemclick()
    }

    private fun onitemclick() {
        categorylistAdapter.itemclick={ meals->
            val intent= Intent(this,MealActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setuprv() {
        binding.categoryrecyclerview.apply {
            layoutManager=GridLayoutManager(this@CategoryActivity,2,GridLayoutManager.VERTICAL,false)
            adapter=categorylistAdapter
        }
    }

    private fun setupmv() {
        cVm.categorylivedata().observe(this, Observer { meallist->
            categorylistAdapter.getcategoryitems(meallist)
        })
    }

    private fun onitemsow() {
        val cateorytitle=intent.getStringExtra(HomeFragment.Categoryname)
       categorytitle.setText(cateorytitle)
    }
}
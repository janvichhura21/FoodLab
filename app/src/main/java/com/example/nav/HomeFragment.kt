package com.example.nav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nav.activity.CategoryActivity
import com.example.nav.adapter.CategoryPopularItemsAdapter
import com.example.nav.adapter.MostPopularAdapter
import com.example.nav.databinding.FragmentHomeBinding
import com.example.nav.db.MealDatabase
import com.example.nav.model.Category
import com.example.nav.model.MealX
import com.example.nav.model.MealXX
import com.example.nav.viewmodel.MainViewModel
import com.example.nav.viewmodel.MainViewModelFactory

class HomeFragment : Fragment() {
     lateinit var binding: FragmentHomeBinding
     lateinit var mainViewModel: MainViewModel
     lateinit var popularAdapter: MostPopularAdapter
     lateinit var mealadapter:CategoryPopularItemsAdapter
     lateinit var randomMealX: MealX
     companion object{
         val mealId="com.example.nav.idMeal"
         val mealname="com.example.nav.nameMeal"
         val mealthimb="com.example.nav.thumbMeal"
         val Categoryname="com.example.nav.categoryname"
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularAdapter= MostPopularAdapter()
        mealadapter= CategoryPopularItemsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mealdb= MealDatabase.getbdinstance(requireContext())
        val mainviemodelfactory= MainViewModelFactory(mealdb)
        mainViewModel= ViewModelProvider(this,mainviemodelfactory)[MainViewModel::class.java]
        binding.cardview.setOnClickListener {
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(mealId,randomMealX.idMeal)
            intent.putExtra(mealname,randomMealX.strMeal)
            intent.putExtra(mealthimb,randomMealX.strMealThumb)
            startActivity(intent)
        }
        mainViewModel.getapi()
        mainViewModel.getmealapi()
        setupcard()
        setupmvcategory()
       setuprv()
        onpopularitemclick()
        mainViewModel.getcateorymealfood()
        setupcategorymv()
        setupcategoryrv()
      oncateorymealclick()
    }

    private fun oncateorymealclick() {
        mealadapter.itemsmeal={ cateory->
            val intent=Intent(activity,CategoryActivity::class.java)
            intent.putExtra(Categoryname,cateory.strCategory)
            startActivity(intent)
        }
    }

    private fun setupcategoryrv() {
        binding.recyclerview1.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=mealadapter
        }
    }
    private fun setupcategorymv() {
        mainViewModel.getcategoryLiveData().observe(viewLifecycleOwner, Observer { categories->
           categories.forEach {
               mealadapter.getcategory(categorymeallist = categories as ArrayList<Category>)
           }
        })
    }

    private fun onpopularitemclick() {
        popularAdapter.items={meal->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(mealId,meal.idMeal)
            intent.putExtra(mealname,meal.strMeal)
            intent.putExtra(mealthimb,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setuprv() {
        binding.recyclerview.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularAdapter
        }
    }

    private fun setupmvcategory() {
      mainViewModel.getcateoryfood().observe(viewLifecycleOwner
      ) {mealList->
          popularAdapter.seameal(mealList as ArrayList<MealXX>)
      }
    }

    private fun setupcard() {
       mainViewModel.getfood().observe(viewLifecycleOwner,object :Observer<MealX>{
           override fun onChanged(t: MealX?) {
               if (t != null) {
                   Glide.with(this@HomeFragment)
                       .load(t.strMealThumb)
                       .into(binding.imageview)
                   this@HomeFragment.randomMealX=t
               }
           }

       })
    }
}
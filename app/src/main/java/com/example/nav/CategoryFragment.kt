package com.example.nav

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nav.adapter.CategoryPopularItemsAdapter
import com.example.nav.databinding.FragmentCategoryBinding
import com.example.nav.db.MealDatabase
import com.example.nav.model.Category
import com.example.nav.viewmodel.MainViewModel
import com.example.nav.viewmodel.MainViewModelFactory
const val mealid="com.example.nav.mealid"
const val mealName="com.example.nav.mealName"
const val mealThumb="com.example.nav.mealthumb"
class CategoryFragment : Fragment() {
   lateinit var binding: FragmentCategoryBinding
   lateinit var cateorAdapter: CategoryPopularItemsAdapter
   lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cateorAdapter= CategoryPopularItemsAdapter()
        val mealdb= MealDatabase.getbdinstance(requireContext())
        val mainviemodelfactory= MainViewModelFactory(mealdb)
        mainViewModel= ViewModelProvider(this,mainviemodelfactory)[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getcateorymealfood()
        observermv()
        onrv()
        onclickitems()
    }

    private fun onclickitems() {
        cateorAdapter.itemsmeal={ mealname->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(mealid,mealname.idCategory)
            intent.putExtra(mealName,mealname.strCategory)
            intent.putExtra(mealThumb,mealname.strCategoryThumb)
            startActivity(intent)
        }
    }

    private fun onrv() {
        binding.categoryrecyclerview.apply {
            layoutManager=GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=cateorAdapter

        }
    }

    private fun observermv() {
      mainViewModel.getcategoryLiveData().observe(viewLifecycleOwner, Observer { meallist->
         cateorAdapter.getcategory(meallist as ArrayList<Category>)
      })
    }
}
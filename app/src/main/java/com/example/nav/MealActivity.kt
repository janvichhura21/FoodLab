package com.example.nav

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.nav.db.MealDatabase
import com.example.nav.model.MealX
import com.example.nav.viewmodel.MealDetailViewModel
import com.example.nav.viewmodel.MealViewModelFactory
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {
    lateinit var mealDetailViewModel: MealDetailViewModel
    lateinit var youtube:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        onloading()
        val mealdatabse=MealDatabase.getbdinstance(this)
        val viewmodel=MealViewModelFactory(mealdatabse)
        mealDetailViewModel= ViewModelProvider(this,viewmodel)[MealDetailViewModel::class.java]
        val mealid=intent.getStringExtra(HomeFragment.mealId)
        val image=intent.getStringExtra(HomeFragment.mealthimb)
        Glide.with(this)
            .load(image)
            .into(mealimage)
        val strnames=intent.getStringExtra(HomeFragment.mealname)
        collapsingtoolbar.title=strnames
        collapsingtoolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        collapsingtoolbar.setExpandedTitleColor(resources.getColor(R.color.white))
        if (mealid != null) {
            mealDetailViewModel.getmealdetail(mealid)
        }

        setupmdv()
        onyoutube()
        addfav()
    }

    private fun addfav() {
        favbtn.setOnClickListener {
            mealtoSave.let {
                if (it != null) {
                    mealDetailViewModel.insertmeal(it)
                    Toast.makeText(this, "Add to favourite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onyoutube() {
        youtubeclick.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtube))
            startActivity(intent)
        }
    }

    var mealtoSave:MealX?=null
    private fun setupmdv() {
        mealDetailViewModel.getdetails().observe(this,object :Observer<MealX>{
            override fun onChanged(t: MealX?) {
                if (t != null) {
                    onResponse()
                    mealtoSave=t
                    categoriesmeals.setText("Cateories: ${t.strCategory}")
                    areameals.setText("Area: ${t.strArea}")
                    instruction.setText(t.strInstructions)
                    youtube= t.strYoutube.toString()
                }
            }

        })
            }

    fun onloading(){
        categoriesmeals.visibility= View.INVISIBLE
        areameals.visibility= View.INVISIBLE
        instruction.visibility= View.INVISIBLE
        progressbar.visibility= View.VISIBLE
    }

    fun onResponse(){
        categoriesmeals.visibility= View.VISIBLE
        areameals.visibility= View.VISIBLE
        instruction.visibility= View.VISIBLE
        progressbar.visibility= View.INVISIBLE
    }
}

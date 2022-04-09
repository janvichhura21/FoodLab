package com.example.nav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nav.databinding.CategoryitemsBinding
import com.example.nav.model.MealXX

class CategoryliistAdapter:RecyclerView.Adapter<CategoryliistAdapter.categorylistviewholder>() {
    lateinit var itemclick:((MealXX) -> Unit)
    private var categoryitems=ArrayList<MealXX>()

    fun getcategoryitems( categoryitems:List<MealXX>){
        this.categoryitems=categoryitems as ArrayList<MealXX>
        notifyDataSetChanged()
    }
    class categorylistviewholder(val binding: CategoryitemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categorylistviewholder {
        return categorylistviewholder(CategoryitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: categorylistviewholder, position: Int) {
        val data=categoryitems[position]
        Glide.with(holder.itemView)
            .load(data.strMealThumb)
            .into(holder.binding.categoryview)
        holder.binding.cateorylist.text=data.strMeal
        holder.itemView.setOnClickListener {
            itemclick.invoke(data)
        }
    }

    override fun getItemCount(): Int {
        return categoryitems.size
    }
}
package com.example.nav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nav.databinding.FavitemsBinding
import com.example.nav.model.MealX

class FavListAdapter:RecyclerView.Adapter<FavListAdapter.favvieholder>() {
    class favvieholder(val binding: FavitemsBinding):RecyclerView.ViewHolder(binding.root)
    val diffutil=object :DiffUtil.ItemCallback<MealX>(){
        override fun areItemsTheSame(oldItem: MealX, newItem: MealX): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealX, newItem: MealX): Boolean {
          return oldItem==newItem
        }

    }

    val differ =AsyncListDiffer(this,diffutil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favvieholder {
       return favvieholder(FavitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: favvieholder, position: Int) {
        val data=differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strMealThumb)
            .into(holder.binding.favimages)
        holder.binding.favtxt.text=data.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}
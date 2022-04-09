package com.example.nav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nav.databinding.CategorymealitemsBinding
import com.example.nav.model.Category

class CategoryPopularItemsAdapter:RecyclerView.Adapter<CategoryPopularItemsAdapter.popularviewholder>() {
    var itemsmeal: ((Category) -> Unit)? =null
    private var categorymeallist=ArrayList<Category>()
    fun getcategory(categorymeallist:ArrayList<Category>){
        this.categorymeallist=categorymeallist
        notifyDataSetChanged()
    }
    class popularviewholder(val binding: CategorymealitemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): popularviewholder {
        return popularviewholder(CategorymealitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: popularviewholder, position: Int) {
      val data=categorymeallist[position]
        Glide.with(holder.itemView)
            .load(data.strCategoryThumb)
            .into(holder.binding.categoryimage)
        holder.binding.categorytxt.text=data.strCategory
        holder.itemView.setOnClickListener {
            itemsmeal?.invoke(data)
        }
    }

    override fun getItemCount(): Int {
        return categorymeallist.size
    }
}
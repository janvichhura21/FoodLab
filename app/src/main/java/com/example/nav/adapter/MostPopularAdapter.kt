package com.example.nav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nav.databinding.PopularmealitemsBinding
import com.example.nav.model.MealXX

class MostPopularAdapter:RecyclerView.Adapter<MostPopularAdapter.myviewholder>(){
    lateinit var items:((MealXX) -> Unit)
   private var itemlist=ArrayList<MealXX>()

    fun seameal(itemlist:ArrayList<MealXX>){
        this.itemlist=itemlist
        notifyDataSetChanged()
    }
    class myviewholder(val binding: PopularmealitemsBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        return myviewholder(PopularmealitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        val data=itemlist[position]
         Glide.with(holder.itemView)
             .load(data.strMealThumb)
             .into(holder.binding.imagecontent)
        holder.itemView.setOnClickListener {
            items.invoke(data)
        }

    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

}



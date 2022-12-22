package com.example.brosports.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brosports.R
import com.example.brosports.activity.CategoryPageActivity
import com.example.brosports.databinding.LayoutCategoryItemBinding
import com.example.brosports.model.CategoryModel




class CategoryAdapter(var context: Context, val list: ArrayList<CategoryModel>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var binding = LayoutCategoryItemBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
       holder.binding.categoryName.text = list[position].category
        Glide.with(context).load(list[position].image).into(holder.binding.categoryImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, CategoryPageActivity::class.java)
            intent.putExtra("category", list[position].category)
            context.startActivity(intent)
        }
        }

    override fun getItemCount(): Int {
        return list.size
    }
}



package com.example.brosports.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brosports.activity.ProductDetailsActivity
import com.example.brosports.databinding.LayoutProductItemBinding
import com.example.brosports.model.AddProductModel

class ProductAdapter(private val context : Context, private val list: ArrayList<AddProductModel>)
    :RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding : LayoutProductItemBinding)
        :RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.category.text = data.productCategory
        holder.binding.price.text = "£" + data.productMrp
        holder.binding.textView2.text = data.productName
        holder.binding.stock.text = "Stock :"+data.stock
        holder.binding.addToCartBtn.text = "Buy Now £" + data.productSp
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java )
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
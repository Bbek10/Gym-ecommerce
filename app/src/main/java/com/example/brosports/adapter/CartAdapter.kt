package com.example.brosports.adapter

import  android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brosports.activity.ProductDetailsActivity
import com.example.brosports.databinding.LayoutCartItemBinding
import com.example.brosports.roomdb.AppDatabase
import com.example.brosports.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context: Context, val list : List<ProductModel>):
RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.productImage)

        holder.binding.productTitle.text = list[position].productName
        holder.binding.productPrice.text = list[position].productSp

                holder.itemView.setOnClickListener {
                    val intent = Intent(context, ProductDetailsActivity::class.java)
                    intent.putExtra("id", list[position].productId)
                    context.startActivity(intent)


                }

                val dao = AppDatabase.getInstance(context).productDao()
                var cartQuantity = 1
                var stock = holder.binding.stockRem.text
                var stocks = Integer.parseInt(stock as String)


                holder.binding.productOrder.text = cartQuantity.toString()
                holder.binding.increment.setOnClickListener {

                        if(stocks !=0){
                            cartQuantity += 1
                            stocks -= 1
                            holder.binding.productOrder.text = cartQuantity.toString()
                            holder.binding.stockRem.text = stocks.toString()
                        }

                }

        holder.binding.decrement.setOnClickListener {
            if(cartQuantity>0){
                    cartQuantity -= 1
                    stocks += 1
                    holder.binding.stockRem.text = stocks.toString()
                    holder.binding.productOrder.text = cartQuantity.toString()

                }

        }
                holder.binding.cancelBtn.setOnClickListener {

                    GlobalScope.launch(Dispatchers.IO) {
                        dao.deleteProduct(
                            ProductModel(
                                list[position].productId,
                                list[position].productName,
                                list[position].productImage,
                                list[position].productSp,
                                list[position].stock,


                                )
                        )

                    }


                }

    }
    override fun getItemCount(): Int {
        return list.size
    }
}







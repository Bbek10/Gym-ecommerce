package com.example.brosports.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.brosports.R
import com.example.brosports.adapter.CategoryProductAdapter
import com.example.brosports.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_page)

        products(intent.getStringExtra("category"))
    }

    private fun products(category: String?) {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory", category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }

                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = CategoryProductAdapter(this,list)
            }


    }
}
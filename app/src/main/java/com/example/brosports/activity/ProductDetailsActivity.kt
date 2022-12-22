package com.example.brosports.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.brosports.MainActivity
import com.example.brosports.databinding.ActivityProductDetailsBinding
import com.example.brosports.roomdb.AppDatabase
import com.example.brosports.roomdb.ProductDao
import com.example.brosports.roomdb.ProductModel

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProductDetails(intent.getStringExtra("id"))


    }

    private fun getProductDetails(prodId: String?) {

        Firebase.firestore.collection("products")
            .document(prodId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name = it.getString("productName")
                val productSp = it.getString("productSp")
                val productDesc = it.getString("productDescription")



                binding.headingTitle.text = name
                binding.priceTxt.text = "£$productSp"
                binding.productDesc.text = productDesc


                val slideList = ArrayList<SlideModel>()

                for(data in list){
                    slideList.add(SlideModel(data, ScaleTypes.CENTER_CROP))
                }

                cartPageAction(prodId, name, productSp, it.getString("productCoverImg"))

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener{
                Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show()

            }

    }

    private fun cart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)

        val editor = preference.edit()
        editor.putBoolean("isCart", true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()



    }
    private fun cartPageAction(prodId: String, name: String?, productSp: String?, coverImg: String?) {
        val productDao = AppDatabase.getInstance(this).productDao()

        if(productDao.isExit(prodId)!= null){
            binding.productDetailsBtn.text = "Go to Cart"

        }else{
            binding.productDetailsBtn.text = "Add to Cart"
        }

        binding.productDetailsBtn.setOnClickListener{
            if(productDao.isExit(prodId) != null){
                cart()


            }else{
                toCart(productDao,prodId,name, productSp,coverImg)

            }
        }

    }



    private fun toCart(
        productDao: ProductDao,
        prodId: String,
        name: String?,
        productSp: String?,
        coverImg: String?) {

        val data = ProductModel(prodId,name,coverImg,productSp)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.productDetailsBtn.text = "Open Cart "
        }

    }

}


package com.example.brosports.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.brosports.activity.AddressPageActivity
import com.example.brosports.activity.LoginPageActivity
import com.example.brosports.adapter.CartAdapter
import com.example.brosports.databinding.FragmentCartBinding
import com.example.brosports.roomdb.AppDatabase
import com.example.brosports.roomdb.ProductModel
import com.google.firebase.auth.FirebaseAuth


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var list: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()
        list = ArrayList()
        dao.getAllProducts().observe(requireActivity()){

            binding.cartRecycler.adapter = CartAdapter(requireContext(), it)
            list.clear()
            for(data in it){
                list.add(data.productId)
            }
            productDetails(it)
        }
        return binding.root
    }
    private fun productDetails(data: List<ProductModel>?) {
        var tot = 0
        for (item in data!!) {
            tot += item.productSp!!.toInt()
        }
        binding.totalItem.text = "Total item ${data.size}"
        binding.totalPrice.text = "Total Cost: $tot"

        binding.checkout.setOnClickListener {

            if (FirebaseAuth.getInstance().currentUser == null) {
                val intent = Intent(requireContext(), LoginPageActivity::class.java)
                startActivity(intent)
            } else {

                val addressPage = Intent(context, AddressPageActivity::class.java)
                val b = Bundle()
                b.putStringArrayList("productIds", list)
                b.putString("totalCost", tot.toString())
                addressPage.putExtras(b)

                startActivity(addressPage)

            }
        }

    }


}
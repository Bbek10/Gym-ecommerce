package com.example.brosports.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brosports.databinding.ActivityAddressPageBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressPageBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var totalCost :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences     = this.getSharedPreferences("user", MODE_PRIVATE)
        totalCost = intent.getStringExtra("totalCost")!!
        loadUserInfo()

        binding.proceed.setOnClickListener{
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.cardExpiry.text.toString(),
                binding.userCity.text.toString(),
                binding.userCard.text.toString(),
                binding.userStreet.text.toString()
            )

        }
    }

    private fun validateData(number: String, name: String, card1: String, city: String, expiry1: String, street: String) {

        if(number.isEmpty() || card1.isEmpty() || name.isEmpty()|| expiry1.isEmpty() || city.isEmpty()){
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData(card1,city,expiry1,street)
        }


    }

    private fun storeData(card1: String, city: String, expiry1: String, street: String) {
        val map = hashMapOf<String, Any>()
        map["street"] = street
        map["expiry1"] = expiry1
        map["city"] = city
        map["card1"] = card1

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)

                val intent = Intent(this, CheckoutPageActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)


            }.addOnFailureListener{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userStreet.setText(it.getString("street"))
                binding.userCity.setText(it.getString("city"))
                binding.userCard.setText(it.getString("card1"))
                binding.cardExpiry.setText(it.getString("expiry1"))

            }

    }
}
package com.example.brosports.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brosports.databinding.ActivityUserPageBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserPageBinding

    private lateinit var preferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInfo()

        binding.updateBtn.setOnClickListener{
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.fullName.text.toString(),
                binding.gender.text.toString(),
                binding.userCity.text.toString()
            )


        }
    }

    private fun validateData(number: String, name: String, fullName: String, gender: String, city:String) {

        if(number.isEmpty() || fullName.isEmpty() || name.isEmpty() || gender.isEmpty() || city.isEmpty() ){
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData(fullName,gender,city)
        }


    }

    private fun storeData(fullName: String, gender: String, city: String) {
        val map = hashMapOf<String, Any>()
        map["fullName"] = fullName
        map["gender"] = gender
        map["city"] = city

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                Toast.makeText(this, "Details Updated", Toast.LENGTH_SHORT).show()


            }.addOnFailureListener{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.usernameImg.text = (it.getString("username"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userCity.setText(it.getString("city"))
                binding.fullName.setText(it.getString("fullName"))
                binding.gender.setText(it.getString("gender"))

            }

    }
}
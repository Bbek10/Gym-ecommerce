package com.example.brosports.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brosports.MainActivity
import com.example.brosports.databinding.ActivityCheckoutPageBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CheckoutPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutPageBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var totalCost :String


    lateinit var exp1 :String
    lateinit var exp2 :String
    lateinit var card1:String
    lateinit var card20:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences     = this.getSharedPreferences("user", MODE_PRIVATE)
        totalCost = intent.getStringExtra("totalCost")!!
        loadUserInfo()


        binding.checkoutBtn.setOnClickListener{
            validateData(
                binding.userNumber.text.toString(),
                binding.userName.text.toString(),
                binding.userExpiry.text.toString(),
                binding.userCard.text.toString()

            )

            Firebase.firestore.collection("users")
                .document(preferences.getString("number","")!!)
                .get().addOnSuccessListener {
                    exp1 = it.getString("expiry1").toString()
                    exp2 = it.getString("expiry2").toString()
                    card1 = it.getString("card1").toString()
                    card20 = it.getString("card2").toString()



                }

        }


        }


    private fun validateData(number: String, name: String, expiry2: String,  card2: String) {

        if(number.isEmpty() || expiry2.isEmpty() || card2.isEmpty() ){
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData(expiry2,card2)
        }


    }

    private fun storeData(expiry2: String, card2: String) {
        val map = hashMapOf<String, Any>()
        map["card2"] = card2
        map["expiry2"] = expiry2

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)

                if(exp1 == exp2 && card1 == card20){
                    Toast.makeText(this, "Order Successful, Continue Shopping", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtras(b)
                    startActivity(intent)


                }else{
                    Toast.makeText(this, "Something went Wrong! ", Toast.LENGTH_SHORT).show()
                }

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
                binding.userCard.setText(it.getString("card2"))
                binding.userExpiry.setText(it.getString("expiry2"))


            }

    }
}
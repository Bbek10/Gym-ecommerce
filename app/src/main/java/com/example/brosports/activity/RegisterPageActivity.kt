package com.example.brosports.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.brosports.databinding.ActivityRegisterPageBinding
import com.example.brosports.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterPageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterPageBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.signIn.setOnClickListener{

            login()
        }
        binding.signInBtn.setOnClickListener {
            val e = binding.email.text.toString()
            val p = binding.password.text.toString()


            userValidation()
        }

    }

    private fun firebaseStore() {
        val builder = AlertDialog.Builder(this).setTitle("Loading.......")
            .setMessage("Please Wait for a while")
            .setCancelable(false)
            .create()
        builder.show()

        val preferences= this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preferences.edit()

        editor.putString("number",binding.userNumber.text.toString())
        editor.putString("name", binding.userName.text.toString())
        editor.putString("email", binding.email.text.toString())
        editor.putString("password",binding.password.text.toString())
        editor.apply()

        val data = UserModel(userName =  binding.userName.text.toString(), userPhoneNumber = binding.userNumber.text.toString(), email = binding.email.text.toString(), pass = binding.password.text.toString())
        Firebase.firestore.collection("users").document(binding.userNumber.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this,"User Registered. You may Login now.", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                login()

            }
            .addOnFailureListener{
                builder.dismiss()

                Toast.makeText(this,"Error.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun login() {
        startActivity(Intent(this, LoginPageActivity::class.java))
        finish()
    }
    private fun userValidation() {
        if(binding.userName.text!!.isEmpty() || binding.userNumber.text!!.isEmpty())
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        else
            firebaseStore()
    }

}
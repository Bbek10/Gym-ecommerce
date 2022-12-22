package com.example.brosports.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.brosports.MainActivity
import com.example.brosports.databinding.ActivityOtppageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPPageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOtppageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtppageBinding.inflate(layoutInflater)

        binding.verifyOtpBtn.setOnClickListener{
            if(binding.userOTP.text!!.isEmpty())
                Toast.makeText(this,"Please provide OTP", Toast.LENGTH_SHORT).show()
            else{
                userVerification(binding.userOTP.text.toString())
            }
        }
        setContentView(binding.root)
    }

    private fun userVerification(otp: String) {


        val credential = PhoneAuthProvider.getCredential(
            intent.getStringExtra("verificationId")!!,otp)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val preferences= this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()

                    editor.putString("number",intent.getStringExtra("number")!!)
                    editor.apply()
                    startActivity(Intent(this,MainActivity::class.java))
                    Toast.makeText(this, "Login Success, now you can proceed to checkout!", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()


                }
            }
    }
}
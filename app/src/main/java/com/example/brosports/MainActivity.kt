package com.example.brosports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.brosports.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var iter = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav)

        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        binding.bottomBar.onItemSelected = {
            when (it) {
                0 -> {
                    iter = 0;
                    navController.navigate(R.id.homeFragment)
                }
                1 -> iter = 1
                2 -> iter = 2


            }
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        if (iter == 0) {
            finish()
        }
    }
}
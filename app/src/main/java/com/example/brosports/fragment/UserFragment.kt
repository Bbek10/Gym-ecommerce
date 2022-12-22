package com.example.brosports.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brosports.activity.UserPageActivity
import com.example.brosports.databinding.FragmentUserBinding


class UserFragment : Fragment() {


    private lateinit var binding: FragmentUserBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserBinding.inflate(layoutInflater)
        binding.updateDetails.setOnClickListener{
            val intent = Intent(requireContext(), UserPageActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }


}
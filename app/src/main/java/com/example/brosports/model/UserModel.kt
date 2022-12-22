package com.example.brosports.model

data class UserModel(
    val userName: String?= "",
    val userPhoneNumber: String?="",
    val street :String?="",
    val state :String?="",
    val city :String?="",
    val pinCode :String?="",
    val email : String?="",
    val pass : String?="",
    val fullName: String? = "",
    val gender : String? ="",
    var card1: String?="",
    val card2: String? = "",
    var expiry1: String? ="",
    var expiry2: String?=""

    )

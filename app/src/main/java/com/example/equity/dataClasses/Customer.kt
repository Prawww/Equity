package com.example.diceroller.dataClasses

data class Customer(
    val customerAccount: List<CustomerAccount>,
    val email: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val nationalId: String,
    val phoneNumber: String
)

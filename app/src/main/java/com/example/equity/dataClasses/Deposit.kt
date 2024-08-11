package com.example.diceroller.dataClasses

data class Deposit(
    val id: Long = 0,

    // Common properties for all transactions
    val transactionId: String,
    val amount: Double,
    val date: String,
    val isCompleted: Boolean,
    val imageData: String,
    val tellerId: Long,
    val accountId:Long,
    val transactionType: String,
    val depositer: String,
    val depositerId: String,
    val depositerNo: String,
    val currency:String



    )

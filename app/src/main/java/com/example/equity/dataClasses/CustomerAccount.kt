package com.example.diceroller.dataClasses

data class CustomerAccount(
    val accno: Long,
    val balance: Double,
    val id: Long,
    val transactions: List<Transaction>
)

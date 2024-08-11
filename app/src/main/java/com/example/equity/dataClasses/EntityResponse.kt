package com.example.diceroller.dataClasses

data class EntityResponse<T>(
    val entity: T?,
    val message: String,
    val statusCode: Int)
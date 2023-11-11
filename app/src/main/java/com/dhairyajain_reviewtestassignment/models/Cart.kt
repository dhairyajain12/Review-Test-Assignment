package com.dhairyajain_reviewtestassignment.models

data class Cart(
    val id: Int,
    val userId: Int,
    val date: String,
    val products: Product
)

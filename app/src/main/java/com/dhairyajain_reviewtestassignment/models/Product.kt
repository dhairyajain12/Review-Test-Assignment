package com.dhairyajain_reviewtestassignment.models

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

class Rating(
    val rate: Double,
    val count: Int)

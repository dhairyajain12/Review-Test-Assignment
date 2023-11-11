package com.dhairyajain_reviewtestassignment.models

data class Users(
    val address: Address,
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val name: Name,
    val phone: String)

class Name(
    val firstname: String,
    val lastname: String)

class Address(
    val geolocation: Geolocation,
    val city: String,
    val street: String,
    val number: Int,
    val zipcode: String)

class Geolocation(
    val lat: Double,
    val long: Double)

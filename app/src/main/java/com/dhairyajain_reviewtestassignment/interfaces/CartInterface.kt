package com.dhairyajain_reviewtestassignment.interfaces

import com.dhairyajain_reviewtestassignment.models.Cart
import retrofit2.Call
import retrofit2.http.GET

interface CartInterface {
    @GET("carts/")
    fun getCartData(): Call<List<Cart>>
}

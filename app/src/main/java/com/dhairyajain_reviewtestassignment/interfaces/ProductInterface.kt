package com.dhairyajain_reviewtestassignment.interfaces

import com.dhairyajain_reviewtestassignment.models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductInterface {
    @GET("products/")
    fun getProductData(): Call<List<Product>>
}

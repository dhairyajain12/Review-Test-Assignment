package com.dhairyajain_reviewtestassignment.interfaces

import com.dhairyajain_reviewtestassignment.models.Users
import retrofit2.Call
import retrofit2.http.GET

interface UserInterface {
    @GET("users/")
    fun getUsersData(): Call<List<Users>>
}

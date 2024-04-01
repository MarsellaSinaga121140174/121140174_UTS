package com.example.uts_pam.network

import com.example.uts_pam.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/users")
    fun getListUsers(@Query("per_page") perPage: String): Call<ResponseUser>

    @GET("api/users/{id}")
    fun getUser(@Path("id") id: String): Call<ResponseUser>
}
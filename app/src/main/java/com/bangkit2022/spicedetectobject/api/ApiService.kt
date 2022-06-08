package com.bangkit2022.spicedetectobject.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("spices")
    fun getSpices(
    ): Call<ResponseSpices>

    @GET("spices/{name}")
    fun getDetailSpices(
        @Path("name") name: String
    ): Call<ResponseItemSpices>
}
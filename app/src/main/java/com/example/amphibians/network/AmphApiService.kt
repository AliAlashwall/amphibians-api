package com.example.amphibians.network

import com.example.amphibians.data.Amphibians
import retrofit2.http.GET


interface AmphApiService {
    @GET("amphibians")
    suspend fun getAmphibians() : List<Amphibians>
}

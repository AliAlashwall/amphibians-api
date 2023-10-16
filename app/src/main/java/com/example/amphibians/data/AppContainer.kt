package com.example.amphibians.data

import com.example.amphibians.network.AmphApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibiansRepository : AmphibiansRepository
}

class DefaultAppContainer() : AppContainer{

    private  val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"

    val retrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()
    val retrofitService : AmphApiService by lazy {
        retrofit.create(AmphApiService ::class.java)
    }
    override val amphibiansRepository: AmphibiansRepository by lazy {
        NetworkAmphibiansRepository(retrofitService)
    }
}
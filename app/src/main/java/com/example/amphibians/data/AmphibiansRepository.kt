package com.example.amphibians.data

import com.example.amphibians.network.AmphApiService

interface AmphibiansRepository {
    suspend fun getAmph() : List<Amphibians>
}

class NetworkAmphibiansRepository(private val retrofitService : AmphApiService) : AmphibiansRepository{
    override suspend fun getAmph(): List<Amphibians> {
        return retrofitService.getAmphibians()
    }

}
package com.example.ampapp.Network

import com.example.ampapp.Model.Amp
import retrofit2.http.GET

interface AmpAPIservice {
    @GET("amphibians")
    suspend fun Getamp():List<Amp>
}
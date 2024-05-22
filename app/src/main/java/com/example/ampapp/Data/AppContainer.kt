package com.example.ampapp.Data

import com.example.ampapp.Network.AmpAPIservice
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val ampPhotoRepo: AmpPhotoRepo
}

class DefaultAppContainer: AppContainer{
    private val baseURL = "https://android-kotlin-fun-mars-server.appspot.com"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseURL)
        .build()
    private val retrofitService: AmpAPIservice by lazy { // this is an object creation
        retrofit.create(AmpAPIservice::class.java)
    }
    override val ampPhotoRepo: AmpPhotoRepo by lazy {
        NetworkAmpPhotoRepo(retrofitService)

    }

}
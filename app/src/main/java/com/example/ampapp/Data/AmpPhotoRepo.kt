package com.example.ampapp.Data

import com.example.ampapp.Model.Amp
import com.example.ampapp.Network.AmpAPIservice

interface AmpPhotoRepo {
    suspend fun getAmpsphotos():List<Amp>
}

class NetworkAmpPhotoRepo(private val ampAPIservice: AmpAPIservice):AmpPhotoRepo{
    override suspend fun getAmpsphotos():List<Amp> = ampAPIservice.Getamp()

}
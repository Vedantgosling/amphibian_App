package com.example.ampapp.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Amp(
    val description: String,
    @SerialName("img_src") val imgSrc:String,
    val name: String,
    val type: String
)
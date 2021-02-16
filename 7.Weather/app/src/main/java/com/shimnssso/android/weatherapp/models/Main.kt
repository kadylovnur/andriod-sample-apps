package com.shimnssso.android.weatherapp.models

import java.io.Serializable

data class Main(
    val temp: Float,
    val pressure: Int,
    val humidity: Int,
    val tempMin: Float,
    val tempMax: Float
) : Serializable
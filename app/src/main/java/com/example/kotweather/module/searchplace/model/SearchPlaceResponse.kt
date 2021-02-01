package com.example.kotweather.module.searchplace.model

import com.example.kotweather.model.Place

data class SearchPlaceResponse (
        val status: String,
        val places: MutableList<Place>
)
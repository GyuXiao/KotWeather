package com.example.kotweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChoosePlaceData(
        @PrimaryKey(autoGenerate = true)
        var privateKey: Int,
        var isLocation: Boolean,
        val name: String,
        val temperature: Int,
        val skycon: String
)
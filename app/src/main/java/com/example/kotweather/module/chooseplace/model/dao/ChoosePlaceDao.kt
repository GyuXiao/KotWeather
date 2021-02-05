package com.example.kotweather.module.chooseplace.model.dao

import androidx.room.*
import com.example.kotweather.model.ChoosePlaceData

@Dao
interface ChoosePlaceDao {
    @Transaction
    @Insert(entity = ChoosePlaceData::class)
    suspend fun insertPlace(choosePlaceData: ChoosePlaceData) : Long

    @Transaction
    @Query("SELECT * FROM choosePlaceData")
    suspend fun queryAllPlace() : MutableList<ChoosePlaceData>

    @Transaction
    @Query("SELECT * FROM choosePlaceData WHERE name = (:name)")
    suspend fun queryChoosePlaceByName(name : String) : ChoosePlaceData?

    @Transaction
    @Query("UPDATE choosePlaceData SET temperature = (:temperature), skycon = (:skycon) WHERE name = (:name)")
    suspend fun updateChoosePlace(temperature: Int, skycon: String, name : String)

    @Transaction
    @Delete(entity = ChoosePlaceData::class)
    suspend fun deleteChoosePlace(choosePlaceData: ChoosePlaceData) : Int
}
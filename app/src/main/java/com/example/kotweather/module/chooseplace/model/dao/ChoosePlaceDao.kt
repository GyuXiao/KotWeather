package com.example.kotweather.module.chooseplace.model.dao

import androidx.room.*
import com.example.kotweather.model.ChoosePlaceData

/**
 * ChoosePlace表主要是记录天气信息
 */
@Dao
interface ChoosePlaceDao {
    @Transaction
    @Insert(entity = ChoosePlaceData::class)
    suspend fun insertPlace(choosePlaceData: ChoosePlaceData) : Long

    @Transaction
    @Query("SELECT * FROM choosePlaceData ORDER BY privateKey desc")
    suspend fun queryAllPlace() : MutableList<ChoosePlaceData>

    @Transaction
    @Query("SELECT * FROM choosePlaceData WHERE name = (:name)")
    suspend fun queryChoosePlaceByName(name : String) : ChoosePlaceData?

    @Transaction
    @Query("UPDATE choosePlaceData SET temperature = (:temperature), skycon = (:skycon) WHERE name = (:name)")
    suspend fun updateChoosePlace(temperature: Int, skycon: String, name : String)

    @Transaction
    @Query("UPDATE choosePlaceData SET isLocation = (:isUnVisible)")
    suspend fun setLocationTagUnVisible(isUnVisible: Boolean)

    @Transaction
    @Delete(entity = ChoosePlaceData::class)
    suspend fun deleteChoosePlace(choosePlaceData: ChoosePlaceData) : Int
}
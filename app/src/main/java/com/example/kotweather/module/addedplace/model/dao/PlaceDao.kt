package com.example.kotweather.module.addedplace.model.dao

import androidx.room.*
import com.example.kotweather.model.Place

/**
 * Room使用第二步
 * Dao是数据访问对象的意思，通常会在这里对数据库的各项操作进行封装，目的是避免逻辑层直接和底层数据库打交道，
 * 这里直接和PlaceDao交互即可
 */
@Dao
interface PlaceDao {
    @Transaction
    @Insert(entity = Place::class)
    suspend fun insertPlace(place: Place): Long

    @Transaction
    @Query("SELECT * FROM place")
    suspend fun queryAllPlace(): MutableList<Place>

    @Transaction
    @Query("SELECT * FROM place WHERE name = (:name)")
    suspend fun queryPlaceByName(name: String): Place?

    @Transaction
    @Query("SELECT * FROM place order by primaryKey desc")
    suspend fun queryFirstPlace() : Place?

    @Transaction
    @Delete(entity = Place::class)
    suspend fun deleteArticle(place: Place): Int

    @Transaction
    @Query("DELETE FROM place")
    suspend fun deleteAll()
}
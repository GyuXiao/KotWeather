package com.example.kotweather.module.chooseplace.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotweather.model.LocationTypeConverter
import com.example.kotweather.model.Place
import com.example.kotweather.module.chooseplace.model.dao.PlaceDao

/**
 * Room使用第三步
 * 定义database，只需要写好三个部分
 * 1，数据库的版本号，2，包含哪些实体类，3，提供Dao层的访问实例
 */
@Database(entities = [Place::class], version = 1, exportSchema = false)
@TypeConverters(LocationTypeConverter::class)
abstract class PlaceDataBase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        private var instance: PlaceDataBase? = null
        fun getInstance(context: Context): PlaceDataBase? {
            if (instance == null) {
                synchronized(PlaceDataBase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                                context.applicationContext,
                                PlaceDataBase::class.java,
                                "database_weather").build()
                    }
                }
            }
            return instance
        }
    }
}
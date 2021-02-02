package com.example.kotweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Entity
 * 用于定义封装实际数据的实体类，每个实体类都会在数据库中有一张对应的表，并且表中的列是根据实体类中的字段自动生成的
 */
@Entity
data class Place(
        @PrimaryKey(autoGenerate = true)
        var primaryKey: Int,// 主键，并把autoGenerate参数指定为true，使得主键的值是自动生成的

        var name: String,
        val location: Location,
        @SerializedName("formatted_address") val address: String = ""
) {
    constructor() : this(0, "", Location("", ""), "")
}
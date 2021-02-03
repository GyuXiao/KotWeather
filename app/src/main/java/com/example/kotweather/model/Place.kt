package com.example.kotweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Room使用第一步
 * Entity
 * 用于定义封装实际数据的实体类，每个实体类都会在数据库中有一张对应的表，并且表中的列是根据实体类中的字段自动生成的
 *
 * 另外，数据类中定义的类与属性，完全是按照搜索城市数据接口返回的JSON格式来定义的
 */
@Entity
data class Place(
        @PrimaryKey(autoGenerate = true)
        var primaryKey: Int,// 主键，并把autoGenerate参数指定为true，使得主键的值是自动生成的

        var name: String,
        val location: Location,
        @SerializedName("formatted_address")//由于JSON中一些字段可能与Kotlin的命名规范不一致，因此这里使用@SerializedName注解的方式，来让JSON字段与Kotlin字段建立映射方式
        val address: String = ""
) {
    constructor() : this(0, "", Location("", ""), "")
}
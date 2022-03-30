package com.edna.android.push.demo_x.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<ButtonActionEntity>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ButtonActionEntity>::class.java).toList()

    @TypeConverter
    fun longListToJson(value: List<Long>?): String = Gson().toJson(value)

    @TypeConverter
    fun longJsonToList(value: String) = Gson().fromJson(value, Array<Long>::class.java).toList()

    @TypeConverter
    fun mapToJson(value: Map<String, String?>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToMap(value: String): HashMap<String, String?> =
        Gson().fromJson(value, HashMap<String, String?>().javaClass)
}
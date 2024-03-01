package com.example.apilist.model

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>?) : String {
        val result : String
        if (list != null){
            result = list.joinToString(",")
        }
        else {
            result = ""
        }
        return result
    }

    @TypeConverter
    fun fromStringToList(string:String?) : List<String> {
        var result : List<String> = emptyList()
        if (string != null) {
            result = string.split(",")
        }
        return result
    }

    @TypeConverter
    fun fromIntListToString(nationalDexNumbers: List<Int>) : String {
        var string = ""
        if (nationalDexNumbers != null) {
            string = nationalDexNumbers.joinToString(",")
        }
        return string
    }
    @TypeConverter
    fun fromStringToIntList(string:String) : List<Int> {
        val stringList = string.split(",")
        val intList = stringList.map { it.toInt() }
        return intList
    }

    @TypeConverter
    fun fromImagesToString(images: Images) : String {
        return "${images.large},${images.small}"
    }

    @TypeConverter
    fun fromStringToImages(string:String) : Images {
        val large=string.split(",")[0]
        val small=string.split(",")[1]
        return Images(large, small)
    }


}
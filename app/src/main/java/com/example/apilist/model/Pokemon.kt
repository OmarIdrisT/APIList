package com.example.apilist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PokemonEntity")
data class Pokemon (
    @PrimaryKey(autoGenerate = false) val id: String = "0",
    val name:String,
    val type: List<String>,
    val level: String,

)

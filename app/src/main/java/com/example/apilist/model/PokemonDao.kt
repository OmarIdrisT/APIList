package com.example.apilist.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface PokemonDao {
    @Query("SELECT * FROM PokemonEntity")
    suspend fun getCards() : MutableList<Data>
    @Query("SELECT * FROM PokemonEntity where id = :pokemonId")
    suspend fun getCardsById(pokemonId: String): MutableList<Data>
    @Insert
    suspend fun addPokemon(pokemon: Data)
    @Delete
    suspend fun deletePokemon(pokemon: Data)
}
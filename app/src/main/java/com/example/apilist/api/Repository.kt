package com.example.apilist.api

import com.example.apilist.model.Pokemon
import com.example.apilist.model.PokemonApplication

class Repository {

    val apiInterface = APIInterface.create()
    val daoInterfase = PokemonApplication.database.pokemonDao()


    suspend fun getAllCards() = apiInterface.getCards()
    suspend fun getCardsById(id: String) = apiInterface.getCardById(id)

    suspend fun saveAsFavorite(pokemon: Pokemon) = daoInterfase.addPokemon(pokemon)
    suspend fun deleteFavorite(pokemon: Pokemon) = daoInterfase.deletePokemon(pokemon)
    suspend fun isFavorite(pokemon: Pokemon) = daoInterfase.getCardsById(pokemon.id).isNotEmpty()
    suspend fun getFavorites() = daoInterfase.getCards()
}
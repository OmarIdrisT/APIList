package com.example.apilist.api

import com.example.apilist.model.Data
import com.example.apilist.model.PokemonApplication
import com.example.apilist.model.PokemonDetails

class Repository {

    val apiInterface = APIInterface.create()
    val daoInterfase = PokemonApplication.database.pokemonDao()


    suspend fun getAllCards() = apiInterface.getCards()
    suspend fun getCardsById(id: String) = apiInterface.getCardById(id)
    suspend fun getFilteredCards(searchPokemon:String) = apiInterface.getSearchedCards(searchPokemon)

    suspend fun saveAsFavorite(pokemon: Data) = daoInterfase.addPokemon(pokemon)
    suspend fun deleteFavorite(pokemon: Data) = daoInterfase.deletePokemon(pokemon)
    suspend fun isFavorite(pokemon: Data) = daoInterfase.getCardsById(pokemon.id).isNotEmpty()
    suspend fun getFavorites() = daoInterfase.getCards()

}
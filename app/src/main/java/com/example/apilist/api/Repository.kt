package com.example.apilist.api

import com.example.apilist.viewmodel.APIViewModel

class Repository {

    val apiInterface = APIInterface.create()


    suspend fun getAllCards() = apiInterface.getCards()
    suspend fun getCardsById(id: String) = apiInterface.getCardById(id)
}
package com.example.apilist.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apilist.api.Repository
import com.example.apilist.model.CardList
import com.example.apilist.model.Data
import com.example.apilist.model.PokemonDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APIViewModel: ViewModel() {

    private val repository = Repository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _cards = MutableLiveData<CardList>()
    val cards = _cards

    fun getCards(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getAllCards()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    _cards.value = response.body()
                    _loading.value = false
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }

    private var _cardDetails = MutableLiveData<PokemonDetails>()
    val cardDetails = _cardDetails
    var id: String = ""

    fun getCardById() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getCardsById(id)
            withContext(Dispatchers.Main) {
                if(response.isSuccessful){
                    _cardDetails.value = response.body()
                    _loading.value = false
                }
                else{
                    Log.e("Error :", response.message())
                }
            }
        }
    }
    fun setIDx(identificar:String){
        this.id=identificar
    }

    private val _isFavorite = MutableLiveData(false)
    val isFavorite = _isFavorite
    private val _favorites = MutableLiveData<MutableList<Data>>()
    val favorites = _favorites

    fun getFavorites() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFavorites()
            withContext(Dispatchers.Main) {
                _favorites.value = response
                _loading.value = false
            }
        }
    }

    fun isFavorite(pokemon: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isFavorite(pokemon)
            withContext(Dispatchers.Main) {
                _isFavorite.value = response
            }
        }
    }

    fun saveAsFavorite(pokemon: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.saveAsFavorite(pokemon)
            _isFavorite.postValue(true)
        }
    }

    fun deleteFavorite(pokemon: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavorite(pokemon)
            _isFavorite.postValue(false)
        }
    }
}

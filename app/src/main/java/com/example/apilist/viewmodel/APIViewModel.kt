package com.example.apilist.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apilist.api.Repository
import com.example.apilist.model.CardList
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
}
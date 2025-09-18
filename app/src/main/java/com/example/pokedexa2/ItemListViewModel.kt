package com.example.pokedexa2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexa2.network.ItemListItem
import com.example.pokedexa2.network.PokemonApi
import kotlinx.coroutines.launch

// ViewModel para a tela de lista de itens
class ItemListViewModel : ViewModel() {

    var itemList by mutableStateOf<List<ItemListItem>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchItemList()
    }

    private fun fetchItemList() {
        viewModelScope.launch {
            try {
                // Chama a nova função da nossa API
                val response = PokemonApi.retrofitService.getItemList()
                itemList = response.results
            } catch (e: Exception) {
                errorMessage = "Falha ao carregar itens: ${e.message}"
            }
        }
    }
}
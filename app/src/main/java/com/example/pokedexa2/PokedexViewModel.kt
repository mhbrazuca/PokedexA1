package com.example.pokedexa2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexa2.network.PokemonApi
import com.example.pokedexa2.network.PokemonListItem
import kotlinx.coroutines.launch
import java.util.Locale

class PokedexViewModel : ViewModel() {

    private var fullPokemonList by mutableStateOf<List<PokemonListItem>>(emptyList())

    
    var searchText by mutableStateOf("")
        private set

    
    val filteredPokemonList: List<PokemonListItem>
        get() = if (searchText.isBlank()) {
            fullPokemonList
        } else {
            fullPokemonList.filter {
                it.name.contains(searchText, ignoreCase = true)
            }
        }

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchPokemonList()
    }

    
    fun onSearchTextChange(newText: String) {
        searchText = newText
    }

    private fun fetchPokemonList() {
        viewModelScope.launch {
            try {
                val response = PokemonApi.retrofitService.getPokemonList()
                // agora guardamos a resposta na lista completa e privada
                fullPokemonList = response.results
            } catch (e: Exception) {
                errorMessage = "Falha ao carregar dados: ${e.message}"
            }
        }
    }
}

fun String.capitalizeFirst(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

package com.example.pokedexa2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexa2.network.PokemonApi
import com.example.pokedexa2.network.PokemonDetailResponse
import kotlinx.coroutines.launch

// dados para a tela de detalhes de 1 Pokémon
class PokemonDetailViewModel : ViewModel() {

    
    var pokemonDetails by mutableStateOf<PokemonDetailResponse?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    
    fun fetchPokemonDetails(pokemonName: String) {
        viewModelScope.launch {
            
            pokemonDetails = null
            errorMessage = null
            try {
                // endpoint da API
                val details = PokemonApi.retrofitService.getPokemonDetail(pokemonName.lowercase())
                pokemonDetails = details
            } catch (e: Exception) {
                errorMessage = "Falha ao buscar detalhes: ${e.message}"
            }
        }
    }
}

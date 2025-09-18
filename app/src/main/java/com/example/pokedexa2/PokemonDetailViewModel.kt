package com.example.pokedexa2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexa2.network.PokemonApi
import com.example.pokedexa2.network.PokemonDetailResponse
import kotlinx.coroutines.launch

// Este ViewModel gerencia os dados para a tela de detalhes de UM Pokémon
class PokemonDetailViewModel : ViewModel() {

    // Guarda os detalhes do Pokémon buscado.
    // O valor pode ser nulo no início, enquanto ainda não carregou.
    var pokemonDetails by mutableStateOf<PokemonDetailResponse?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Esta função será chamada pela tela para iniciar a busca dos dados.
    // Ela recebe o nome do Pokémon que foi clicado.
    fun fetchPokemonDetails(pokemonName: String) {
        viewModelScope.launch {
            // Limpa os detalhes antigos antes de uma nova busca
            pokemonDetails = null
            errorMessage = null
            try {
                // Chama o endpoint de detalhes da nossa API
                val details = PokemonApi.retrofitService.getPokemonDetail(pokemonName.lowercase())
                pokemonDetails = details
            } catch (e: Exception) {
                errorMessage = "Falha ao buscar detalhes: ${e.message}"
            }
        }
    }
}
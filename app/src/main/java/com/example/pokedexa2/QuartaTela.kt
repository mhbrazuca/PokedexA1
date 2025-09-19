package com.example.pokedexa2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokedexa2.ui.theme.PokedexMobileTheme

class QuartaTela : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val pokemonName = intent.getStringExtra("POKEMON_NAME") ?: "Nenhum"

        setContent {
            PokedexMobileTheme {
                
                PokemonDetailScreen(pokemonName = pokemonName)
            }
        }
    }
}

@Composable
fun PokemonDetailScreen(pokemonName: String, detailViewModel: PokemonDetailViewModel = viewModel()) {
    // LaunchedEffect é pra chamar uma função suspend (busca na API)
    // ele vai chamar 'fetchPokemonDetails' apenas uma vez quando a tela abrir.
    LaunchedEffect(Unit) {
        detailViewModel.fetchPokemonDetails(pokemonName)
    }

    val pokemonDetails = detailViewModel.pokemonDetails

    Surface(modifier = Modifier.fillMaxSize()) {
    
        if (pokemonDetails == null) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonDetails.id}.png",
                    contentDescription = pokemonDetails.name,
                    modifier = Modifier.size(250.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                
                Text(
                    text = pokemonDetails.name.capitalizeFirst(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pokemonDetails.types.forEach { typeEntry ->
                        PokemonTypeChip(type = typeEntry.type.name)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Status", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    pokemonDetails.stats.forEach { statEntry ->
                        StatRow(statName = statEntry.stat.name, statValue = statEntry.baseStat)
                    }
                }
            }
        }
    }
}


@Composable
fun PokemonTypeChip(type: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(getColorForType(type))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = type.capitalizeFirst(),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun StatRow(statName: String, statValue: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = statName.capitalizeFirst(), modifier = Modifier.width(120.dp))
        Text(text = statValue.toString(), fontWeight = FontWeight.Bold, modifier = Modifier.width(40.dp))
       
        LinearProgressIndicator(
            progress = { statValue / 200f }, 
            modifier = Modifier.height(8.dp).clip(RoundedCornerShape(4.dp))
        )
    }
}


fun getColorForType(type: String): Color {
    return when (type.lowercase()) {
        "normal" -> Color(0xFFA8A77A)
        "fire" -> Color(0xFFEE8130)
        "water" -> Color(0xFF6390F0)
        "electric" -> Color(0xFFF7D02C)
        "grass" -> Color(0xFF7AC74C)
        "ice" -> Color(0xFF96D9D6)
        "fighting" -> Color(0xFFC22E28)
        "poison" -> Color(0xFFA33EA1)
        "ground" -> Color(0xFFE2BF65)
        "flying" -> Color(0xFFA98FF3)
        "psychic" -> Color(0xFFF95587)
        "bug" -> Color(0xFFA6B91A)
        "rock" -> Color(0xFFB6A136)
        "ghost" -> Color(0xFF735797)
        "dragon" -> Color(0xFF6F35FC)
        "dark" -> Color(0xFF705746)
        "steel" -> Color(0xFFB7B7CE)
        "fairy" -> Color(0xFFD685AD)
        else -> Color.Gray
    }
}

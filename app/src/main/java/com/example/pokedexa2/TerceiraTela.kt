package com.example.pokedexa2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// Verifique se todos os imports são do 'material3'
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pokedexa2.network.ItemListItem
import com.example.pokedexa2.ui.theme.PokedexMobileTheme

// A anotação para permitir o uso de componentes experimentais
@OptIn(ExperimentalMaterial3Api::class)
class TerceiraTela : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexMobileTheme {
                ItemScreen(itemListViewModel = viewModel())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(itemListViewModel: ItemListViewModel) {
    val itemList = itemListViewModel.itemList

    Scaffold(
        topBar = {
            // Agora o TopAppBar deve estar correto
            TopAppBar(title = { Text("Itens da Bolsa") })
        }
    ) { innerPadding ->
        if (itemList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(itemList) { item ->
                    ItemCard(item = item)
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: ItemListItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${item.name}.png",
                contentDescription = item.name,
                modifier = Modifier.size(56.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = item.name.replace("-", " ").capitalizeFirst(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
package com.example.pokedexa2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedexa2.ui.theme.PokedexMobileTheme

class PrimeiraTela : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexMobileTheme {
                // Nossa nova tela de início!
                PokedexHomeScreen()
            }
        }
    }
}

@Composable
fun PokedexHomeScreen() {
    // box para empilhar a cor de fundo e etc
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD32F2F)) // Um vermelho Pokémon bem bonito!
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Image(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = "Pokebola Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "POKÉDEX",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 4.sp // Espaçamento entre as letras
            )

            Spacer(modifier = Modifier.height(60.dp))

            // botões legaizinhos
            val context = androidx.compose.ui.platform.LocalContext.current
            PokedexHomeButton(text = "Ver Pokémon") {
                val intent = Intent(context, SegundaTela::class.java)
                context.startActivity(intent)
            }

            Spacer(modifier = Modifier.height(16.dp))

            PokedexHomeButton(text = "Abrir Bolsa de Itens") {
                val intent = Intent(context, TerceiraTela::class.java)
                context.startActivity(intent)
            }
        }
    }
}


@Composable
fun PokedexHomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, // Fundo do botão
            contentColor = Color.Black    // Cor do texto
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}


@Preview(showBackground = true)
@Composable
fun PokedexHomeScreenPreview() {
    PokedexMobileTheme {
        PokedexHomeScreen()
    }
}

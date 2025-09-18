package com.example.pokedexa2.network

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// --- Data Classes para POKÉMON ---
data class PokemonListResponse(
    @SerializedName("results") val results: List<PokemonListItem>
)
data class PokemonListItem(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {
    fun getPokemonId(): Int {
        return url.split("/").last { it.isNotEmpty() }.toInt()
    }
}
data class PokemonDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("types") val types: List<PokemonTypeEntry>,
    @SerializedName("stats") val stats: List<PokemonStatEntry>,
    @SerializedName("sprites") val sprites: PokemonSprites
)
data class PokemonTypeEntry(@SerializedName("type") val type: PokemonType)
data class PokemonType(@SerializedName("name") val name: String)
data class PokemonStatEntry(@SerializedName("base_stat") val baseStat: Int, @SerializedName("stat") val stat: PokemonStat)
data class PokemonStat(@SerializedName("name") val name: String)
data class PokemonSprites(@SerializedName("front_default") val frontDefault: String)

// --- Data Classes para ITENS (NOVAS) ---
data class ItemListResponse(
    @SerializedName("results") val results: List<ItemListItem>
)

data class ItemListItem(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

// --- Retrofit Service ---

private const val BASE_URL = "https://pokeapi.co/api/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PokeApiService {
    // Busca a lista de Pokémon
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 151): PokemonListResponse

    // Busca detalhes de um Pokémon
    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetailResponse

    // Busca a lista de itens (NOVA FUNÇÃO)
    @GET("item")
    suspend fun getItemList(@Query("limit") limit: Int = 100): ItemListResponse // Buscando 100 itens
}

object PokemonApi {
    val retrofitService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }
}
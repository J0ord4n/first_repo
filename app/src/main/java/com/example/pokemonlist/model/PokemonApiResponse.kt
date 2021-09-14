package com.example.pokemonlist.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PokemonApiResponse(
    @Expose @SerializedName("count") val count: Int,
    @Expose @SerializedName("next") val next: String,
    @Expose @SerializedName("previous") val previous: String,
    @Expose @SerializedName("results") val results: List<PokemonResult>
)

data class PokemonResult(
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("url") val url: String
) {
    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png"
    }

}


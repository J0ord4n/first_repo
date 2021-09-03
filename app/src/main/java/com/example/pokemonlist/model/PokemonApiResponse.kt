package com.example.pokemonlist.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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

data class StatDetail(
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("is_battle_only") val isBattleOnly: Boolean
)


package com.example.pokemonlist.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

data class Pokemon (
    @Expose @SerializedName("id") val id : Int,
    @Expose @SerializedName("name") val name : String?,
    @Expose @SerializedName("weight") val weight : Int,
    @Expose @SerializedName("height") val height : Int,
    @Expose @SerializedName("sprites") val sprites : Sprites,
    @Expose @SerializedName("stats") val stats : List<Stat>,
    @Expose @SerializedName("types") val types : List<Type>,


)

data class Sprites (
    @Expose @SerializedName("front_default") val frontDefault: String?,
    @Expose @SerializedName("front_shiny") val frontShiny : String?
)


data class Stat  (
        val baseStat : Int,
        val effort : Int,
        val stat : StatX
        )
data class StatX(
        val name : String,
        val url: String,
        val hp : Int,
        val defense : Int
)

data class Type(
        val slot : Int,
        val type : TypeX
)

data class TypeX(
        val name : String,
        val url : String
)


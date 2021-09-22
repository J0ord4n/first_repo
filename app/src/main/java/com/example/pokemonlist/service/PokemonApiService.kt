package com.example.pokemonlist.service

import com.example.pokemonlist.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon/{id}")
    fun getPokemonInfo(@Path("id") id: String) : Call<Pokemon>
    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit : Int, @Query("offset") offset : Int) : Call<Pokemon>
}
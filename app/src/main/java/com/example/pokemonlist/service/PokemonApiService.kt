package com.example.pokemonlist.service

import com.example.pokemonlist.model.Pokemon
import com.example.pokemonlist.model.PokemonApiResponse
import com.example.pokemonlist.model.StatDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApiService {
    @GET("pokemon/{id}")
    fun getPokemonInfo(@Path("id") id : Int) : Call<Pokemon>
    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit : Int, @Query("offset") offset : Int) : Call<PokemonApiResponse>
    @GET("stat/{id}")
    fun getPokemonStat(@Path("id") id : Int) : Call<StatDetail>

}
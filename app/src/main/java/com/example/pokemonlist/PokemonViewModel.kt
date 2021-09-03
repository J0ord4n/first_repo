package com.example.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonlist.model.PokemonApiResponse
import com.example.pokemonlist.model.PokemonResult
import com.example.pokemonlist.service.PokemonApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonViewModel() : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: PokemonApiService = retrofit.create(PokemonApiService::class.java)

    val pokemonList = MutableLiveData<List<PokemonResult>>()


    fun getPokemonList() {
        val call = service.getPokemonList(100, 0)

        call.enqueue(object : Callback<PokemonApiResponse> {
            override fun onResponse(
                call: Call<PokemonApiResponse>,
                response: Response<PokemonApiResponse>
            ) {
                response.body()?.results?.let { list ->
                    pokemonList.postValue(list)
                }
            }

            override fun onFailure(call: Call<PokemonApiResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }


}

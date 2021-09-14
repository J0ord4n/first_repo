package com.example.pokemonlist.PokemonInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonlist.model.Pokemon
import com.example.pokemonlist.service.PokemonApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonInfoViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service: PokemonApiService = retrofit.create(PokemonApiService::class.java)

    val pokemonInfo = MutableLiveData<Pokemon>()



    fun getPokemonInfo(id: String) {
        val call = service.getPokemonInfo(id)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let { pokemon ->
                    pokemonInfo.postValue(pokemon)
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                call.cancel()
            }

        })

    }

}
package com.example.pokemonlist.PokemonInfo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokemonlist.R
import com.example.pokemonlist.service.PokemonApiService
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonInfoViewModel
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)



        viewModel = ViewModelProvider(this).get(PokemonInfoViewModel::class.java)


        initUI()
    }

    private fun initUI(){
        val id = intent.extras?.get("id") as Int

        viewModel.getPokemonInfo(id)


        viewModel.pokemonInfo.observe(this, Observer { pokemon ->
            nameTextView.text = pokemon.name
            statsText.text = pokemon.stats[0].stat.defense.toString()
            typeText.text = pokemon.types[0].type.name


            Glide.with(this).load(pokemon.sprites.frontDefault).into(imageView)

        })

    }


}
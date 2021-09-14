package com.example.pokemonlist.PokemonInfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Explode
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pokemonlist.R
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonInfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val explode = Explode()
        window.enterTransition = explode
        window.exitTransition = explode

        viewModel = ViewModelProvider(this).get(PokemonInfoViewModel::class.java)

        initUI()
    }



    @SuppressLint("SetTextI18n")
    private fun initUI() {
        prIndicatorDetail.visibility = View.VISIBLE

        val id = intent.extras?.get("id") as String

        viewModel.getPokemonInfo(id)

        viewModel.pokemonInfo.observe(this, { pokemon ->

            nameTextView.text = pokemon.name
            list_recycler.layoutManager = LinearLayoutManager(this)
            list_recycler.adapter = ListAdapter(pokemon.stats)

            pokemon.types.forEach {
                typeText.text = typeText.text.toString() + " " + it.type.name.toUpperCase(Locale.ROOT)
            }


            Glide.with(this).load(pokemon.sprites.frontDefault).into(imageView)
            prIndicatorDetail.visibility = View.GONE
        })

    }


}
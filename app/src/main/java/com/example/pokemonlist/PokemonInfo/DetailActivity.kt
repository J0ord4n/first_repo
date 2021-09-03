package com.example.pokemonlist.PokemonInfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pokemonlist.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonInfoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this).get(PokemonInfoViewModel::class.java)

        initUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        prIndicatorDetail.visibility = View.VISIBLE

        val id = intent.extras?.get("id") as Int

        viewModel.getPokemonInfo(id)

        viewModel.pokemonInfo.observe(this, Observer { pokemon ->

            nameTextView.text = pokemon.name
            list_recycler.layoutManager = LinearLayoutManager(this)
            list_recycler.adapter = ListAdapter(pokemon.stats)

            pokemon.types.forEach {
                typeText.text = typeText.text.toString() + " " + it.type.name.toUpperCase()
                viewModel.getStatDetail(1)
            }


            Glide.with(this).load(pokemon.sprites.frontDefault).into(imageView)

        })

        viewModel.statDetail.observe(this, Observer { statDetail ->
            Log.d("STAT_NAME", statDetail.name)
            prIndicatorDetail.visibility = View.GONE
        })

    }

}
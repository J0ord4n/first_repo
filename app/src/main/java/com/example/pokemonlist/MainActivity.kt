package com.example.pokemonlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonlist.PokemonInfo.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        initUI()

    }

    private fun initUI(){
        poke_recyclerview.layoutManager = LinearLayoutManager(this)
        poke_recyclerview.adapter = PokemonAdapter{
            val intent = Intent (this, DetailActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        viewModel.getPokemonList()
        viewModel.pokemonList.observe(this, Observer { list ->
            (poke_recyclerview.adapter as PokemonAdapter).setData(list)
        })
    }
}
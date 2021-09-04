package com.example.pokemonlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonlist.PokemonInfo.DetailActivity
import com.example.pokemonlist.model.PokemonResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: PokemonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)


        initUI()

    }

    private fun initUI() {

        prIndicator.visibility = View.VISIBLE
        poke_recyclerview.layoutManager = GridLayoutManager(this, 2)
        poke_recyclerview.adapter = PokemonAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", it.name)
            startActivity(intent)
        }

        viewModel.getPokemonList()
        viewModel.pokemonList.observe(this, Observer { list ->
            (poke_recyclerview.adapter as PokemonAdapter).setData(list)
            prIndicator.visibility = View.GONE
        })



        pokemon_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                (poke_recyclerview.adapter as PokemonAdapter).filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (poke_recyclerview.adapter as PokemonAdapter).filter.filter(newText)
                return false
            }

        })

    }


}





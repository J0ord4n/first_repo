package com.example.pokemonlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.transition.*
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonlist.PokemonInfo.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_pokemon.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val explode = Explode()
        window.enterTransition = explode
        window.exitTransition = explode

        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        initUI()
    }


    private fun initUI() {
        prIndicator.visibility = View.VISIBLE
        poke_recyclerview.layoutManager = GridLayoutManager(this, 2)
        poke_recyclerview.adapter = PokemonAdapter {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", it.name)
            val transition = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this)
            startActivity(intent, transition.toBundle())
        }

        viewModel.getPokemonList()
        viewModel.pokemonList.observe(this, Observer { list ->
            (poke_recyclerview.adapter as PokemonAdapter).setData(list)
            prIndicator.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search Pokemon"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (poke_recyclerview.adapter as PokemonAdapter).filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (poke_recyclerview.adapter as PokemonAdapter).filter.filter(newText)
                return false
            }

        })
        return true
    }
}





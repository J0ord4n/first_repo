package com.example.pokemonlist

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.transition.Explode
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonlist.PokemonInfo.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PokemonViewModel

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val noNetwork = intent.getBooleanExtra(
                ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false
            )
            if (noNetwork) {
                noNetworkFound()
            } else {
                networkFound()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floating_on_off.setOnClickListener { darkmodeDialog() }
        val explode = Explode()
        window.enterTransition = explode
        window.exitTransition = explode
        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    @SuppressLint("ResourceType")
    private fun noNetworkFound() {
        floating_on_off.visibility = View.INVISIBLE
        prIndicator.visibility = View.INVISIBLE
        poke_recyclerview.visibility = View.INVISIBLE
        val errorDialog = AlertDialog.Builder(this)
        errorDialog.setView(R.layout.no_net_layout)
        errorDialog.setPositiveButton("Ok", null)
        errorDialog.show()
    }

    private fun networkFound() {
        floating_on_off.visibility = View.VISIBLE
        prIndicator.visibility = View.VISIBLE
        poke_recyclerview.visibility = View.VISIBLE
        initUI()
    }

    @SuppressLint("ShowToast")
    private fun darkmodeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DarkMode On/Off")
        val choose = arrayOf("Light-Mode", "Dark-Mode")
        val checkedItem = 0
        builder.setSingleChoiceItems(choose, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    delegate.applyDayNight()
                    dialog.dismiss()
                    Toast.makeText(this, "Light-Mode On", Toast.LENGTH_LONG).show()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    delegate.applyDayNight()
                    dialog.dismiss()
                    Toast.makeText(this, "Dark-Mode On", Toast.LENGTH_LONG).show()
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
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
        viewModel.pokemonList.observe(this, { list ->
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





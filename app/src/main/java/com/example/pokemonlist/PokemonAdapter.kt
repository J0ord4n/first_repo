package com.example.pokemonlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlist.model.PokemonResult
import kotlinx.android.synthetic.main.row_pokemon.view.*
import java.util.Collections.emptyList

class PokemonAdapter(val pokemonClick : (Int) -> Unit): RecyclerView.Adapter<PokemonAdapter.PokeViewHolder>() {
    var pokemonList : List<PokemonResult> = emptyList<PokemonResult>()

    fun setData(list : List<PokemonResult>){
        pokemonList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        return PokeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_pokemon, parent, false))
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }


    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.itemView.itemString.text = "${position + 1} - ${pokemon.name}"

        holder.itemView.setOnClickListener { pokemonClick(position + 1) }
    }

    class PokeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}
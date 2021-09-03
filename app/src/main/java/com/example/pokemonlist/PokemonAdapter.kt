package com.example.pokemonlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokemonlist.model.PokemonResult
import kotlinx.android.synthetic.main.row_pokemon.view.*


class PokemonAdapter(val pokemonClick: (Int) -> Unit) :
    RecyclerView.Adapter<PokemonAdapter.PokeViewHolder>(), Filterable {


    private var pokemonList: ArrayList<PokemonResult> = ArrayList()
    private var filterPokemonList: ArrayList<PokemonResult> = ArrayList()


    fun setData(list: List<PokemonResult>) {
        pokemonList = list as ArrayList<PokemonResult>
        filterPokemonList = pokemonList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        return PokeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_pokemon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return filterPokemonList.size
    }


    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = filterPokemonList[position]
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.bounce)
        holder.itemView.itemString.text = pokemon.name.toUpperCase()
        Glide.with(holder.itemView.context)
            .load(pokemon.getImageUrl())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.pokemonImage)

        holder.itemView.setOnClickListener {
            holder.itemView.pokemonImage.startAnimation(animation)
            pokemonClick(position + 1)
        }
    }

    class PokeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filterPokemonList = pokemonList else {
                    val filteredList = ArrayList<PokemonResult>()
                    pokemonList
                        .filter {
                            (it.name.contains(constraint.toString()))
                        }
                        .forEach { filteredList.add(it) }
                    filterPokemonList = filteredList
                }
                return FilterResults().apply { values = filterPokemonList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterPokemonList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<PokemonResult>
                notifyDataSetChanged()

            }

        }
    }
}








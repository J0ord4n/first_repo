package com.example.pokemonlist.PokemonInfo

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonlist.R
import com.example.pokemonlist.model.Stat
import kotlinx.android.synthetic.main.row_list.view.*
import java.util.*

class ListAdapter( private val statList: List<Stat>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_list, parent, false)
        )


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val stat = statList[position]
        val max = statList.maxByOrNull { it.base_stat }
        val pb = holder.itemView.progressStat
        val currentProgress = stat.base_stat
        pb.max = max?.base_stat ?: 200
        holder.itemView.itemList.text = stat.stat.name.toUpperCase(Locale.ROOT)
        holder.itemView.itemNumber.text = stat.base_stat.toString()
        ObjectAnimator.ofInt(pb, "progress", currentProgress)
            .setDuration(1000)
            .start()
    }

    override fun getItemCount(): Int = statList.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
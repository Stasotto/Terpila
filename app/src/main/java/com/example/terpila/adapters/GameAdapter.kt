package com.example.terpila.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.terpila.models.Game

class GameAdapter(
    private val itemGameListener: ItemGameListener
) : RecyclerView.Adapter<GameHolder>() {

    private var gameList = emptyList<Game>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        return GameHolder.from(parent, itemGameListener)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addGames(games: List<Game>) {
        val diffCallback = GameDiffCallback(gameList, games)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        gameList = games
        diffResult.dispatchUpdatesTo(this)
    }
}
package com.example.terpila.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.terpila.R
import com.example.terpila.databinding.ItemGameBinding
import com.example.terpila.models.Game

class GameHolder(
    item: View,
    private val itemGameListener: ItemGameListener
) : RecyclerView.ViewHolder(item) {

    companion object {
        fun from(parent: ViewGroup, itemGameListener: ItemGameListener) = GameHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false),
            itemGameListener
        )
    }

    private val binding by lazy { ItemGameBinding.bind(item) }

    fun bind(game: Game) = with(binding) {
        root.isEnabled = game.isEnable
        gameName.text = game.name
        gameImage.setImageResource(R.drawable.no_photo_available_icon)
        btnGame.setOnClickListener {
            itemGameListener.onPlayButtonClickListener(game)
        }
        gameDifficulty.text = game.levels[game.currentDifficulty].text
        gameDifficulty.setOnClickListener {
            chooseDifficulty(it, game)
        }
    }

    private fun chooseDifficulty(view: View, game: Game) {
//        val listener = DialogInterface.OnClickListener { _, which ->
//            when(which) {
//                DialogInterface.BUTTON_POSITIVE -> itemGameListener.onDifficultyChangedListener(which ga)
//            }
//        }
        val alertDialog = AlertDialog.Builder(view.context)
            .setTitle("Уровень сложности")
            .setSingleChoiceItems(
                AlertDifficultyAdapter(game),
                game.currentDifficulty, null
            )
            .setPositiveButton("Готово") { dialog, _ ->
                val newDifficulty = (dialog as AlertDialog).listView.checkedItemPosition
                itemGameListener.onDifficultyChangedListener(
                    newDifficulty,
                    game
                )
            }
            .create()
        alertDialog.show()
    }
}
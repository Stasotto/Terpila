package com.example.terpila.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.terpila.databinding.ItemAlertDifficultyBinding
import com.example.terpila.models.Game

class AlertDifficultyAdapter(
    private val game: Game
) : BaseAdapter() {
    override fun getCount(): Int {
        return game.levels.size

    }

    override fun getItem(position: Int): Any {
        return game.levels[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = convertView?.tag as ItemAlertDifficultyBinding? ?: createBinding(parent)
        binding.tvDifficulty.text = game.levels[position].text
        return binding.root
    }

    private fun createBinding(parent: ViewGroup?): ItemAlertDifficultyBinding {
        val binding = ItemAlertDifficultyBinding.inflate(LayoutInflater.from(parent?.context))
        binding.root.tag = binding
        return binding

    }
}
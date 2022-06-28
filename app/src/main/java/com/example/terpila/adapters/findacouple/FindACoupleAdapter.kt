package com.example.terpila.adapters.findacouple

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.terpila.fragments.findacouplegame.models.FindACoupleImage

class FindACoupleAdapter(
    private val imageClick: OnImageClick
) : RecyclerView.Adapter<FindACoupleHolder>() {

    private var dataList = mutableListOf<FindACoupleImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindACoupleHolder {
        return FindACoupleHolder.from(parent, imageClick)
    }

    override fun onBindViewHolder(holder: FindACoupleHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addImages(images: List<FindACoupleImage>) {
        dataList = images.toMutableList()
        notifyDataSetChanged()
    }
}
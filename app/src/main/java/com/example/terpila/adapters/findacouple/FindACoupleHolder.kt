package com.example.terpila.adapters.findacouple

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.terpila.R
import com.example.terpila.databinding.ItemImageViewBinding
import com.example.terpila.fragments.findacouplegame.models.FindACoupleImage

class FindACoupleHolder(item: View,
                        private val imageClick: OnImageClick
) : RecyclerView.ViewHolder(item) {

    companion object{
        fun from(parent: ViewGroup, imageClick: OnImageClick) = FindACoupleHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false),
            imageClick
        )
    }

    private val binding by lazy { ItemImageViewBinding.bind(item) }

    fun bind(item: FindACoupleImage) = with(binding) {
        image.setImageDrawable(item.image)
        back.setImageResource(R.drawable.backimage)
        if(item.isClick) {
            image.isVisible = true
            back.isVisible  = false
        } else {
            image.isVisible = false
            back.isVisible  = true

        }
        back.setOnClickListener {
            imageClick.setOnImageClickListener(item, bindingAdapterPosition)
        }
    }
}
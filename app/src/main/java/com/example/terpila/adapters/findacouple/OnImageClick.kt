package com.example.terpila.adapters.findacouple

import com.example.terpila.fragments.findacouplegame.models.FindACoupleImage

interface OnImageClick {

    fun setOnImageClickListener(image: FindACoupleImage, position: Int)
}
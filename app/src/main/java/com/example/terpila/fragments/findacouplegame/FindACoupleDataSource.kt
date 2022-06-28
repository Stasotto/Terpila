package com.example.terpila.fragments.findacouplegame

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.terpila.R
import com.example.terpila.fragments.findacouplegame.models.FindACoupleImage

class FindACoupleDataSource {
        private var counter = 0

        private fun getImage(
            context: Context,
            @DrawableRes id: Int,
            imageId: Int
        ): List<FindACoupleImage> {
            counter++
            return listOf(
                FindACoupleImage(ContextCompat.getDrawable(context, id)!!, imageId),
                FindACoupleImage(ContextCompat.getDrawable(context, id)!!, imageId)
            )
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun getImages(context: Context, amount: Int): List<FindACoupleImage> {
            val list = mutableListOf<FindACoupleImage>()
            with(list) {
                addAll(getImage(context, R.drawable.image1, 1))
                addAll(getImage(context, R.drawable.image2, 2))
                addAll(getImage(context, R.drawable.image3, 3))
                addAll(getImage(context, R.drawable.image4, 4))
                addAll(getImage(context, R.drawable.image5, 5))
                addAll(getImage(context, R.drawable.image6, 6))
                if (counter == amount) return this
                addAll(getImage(context, R.drawable.image7, 7))
                addAll(getImage(context, R.drawable.image8, 8))
                addAll(getImage(context, R.drawable.image9, 9))
                if (counter == amount) return this
                addAll(getImage(context, R.drawable.image10, 10))
                addAll(getImage(context, R.drawable.image11, 11))
                addAll(getImage(context, R.drawable.image12, 12))
                return this
            }
        }
}
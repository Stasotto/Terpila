package com.example.terpila.fragments.findacouplegame

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.terpila.R
import com.example.terpila.adapters.findacouple.FindACoupleAdapter
import com.example.terpila.adapters.findacouple.OnImageClick
import com.example.terpila.contract.navigator
import com.example.terpila.databinding.FragmentFindACoupleBinding
import com.example.terpila.fragments.findacouplegame.models.FindACoupleImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FindACoupleGameFragment : Fragment(R.layout.fragment_find_a_couple) {

    companion object {
        const val EASY = 6
        const val YOU_CAN_DO_IT = 9
        const val HARD = 12
        const val NIGHTMARE = 15
        private const val ITEMS_COUNT = "Row"
        private const val DIFFICULTY = "Difficulty"
        const val TAG = "Find a couple"
        fun newInstance(difficulty: String, rowCount: Int) = FindACoupleGameFragment().apply {
            arguments = bundleOf(ITEMS_COUNT to rowCount, DIFFICULTY to difficulty)
        }
    }

    private var defaultImageList = mutableListOf<FindACoupleImage>()
    private var changedImageList = mutableListOf<FindACoupleImage>()
    private var firstImageId: Int = 0
    private var secondImageId: Int = 0
    private var counter: Int = 0
    private var finishCounter: Int = 0

    private val binding by viewBinding(FragmentFindACoupleBinding::bind)
    private val adapter by lazy { FindACoupleAdapter(onImageClick) }
    private val onImageClick: OnImageClick = object : OnImageClick {

        override fun setOnImageClickListener(image: FindACoupleImage, position: Int) {
            changedImageList[position] = image.copy(isClick = true)
            when (counter) {
                0 -> firstImageId = image.id
                1 -> secondImageId = image.id
                else -> return
            }
            if (counter != 0) {
                adapter.addImages(changedImageList)
                if (firstImageId == secondImageId) {
                    reset()
                    finishCounter++
                    isLast()
                    return
                }
                if (firstImageId != secondImageId) {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500)
                        adapter.addImages(defaultImageList)
                        changedImageList = defaultImageList.toMutableList()
                        reset()
                        finishCounter = 0
                        return@launch
                    }
                }
            }
            counter++
            adapter.addImages(changedImageList)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defaultImageList = FindACoupleDataSource().getImages(
            requireContext().applicationContext,
            requireArguments().getInt(ITEMS_COUNT)
        ).shuffled().toMutableList()
        setUpRecycler()
        changedImageList = defaultImageList.toMutableList()
    }

    override fun onResume() {
        super.onResume()
        binding.chronometer.start()
    }

    override fun onPause() {
        super.onPause()
        binding.chronometer.stop()
    }

    private fun reset() {
        firstImageId = 0
        secondImageId = 0
        counter = 0
    }


    private fun isLast() = with(binding) {
        if (finishCounter == 15) {
            chronometer.stop()
            navigator().showGameResultFragment(
                this::class.java,
                requireArguments().getString(DIFFICULTY)!!,
                ((SystemClock.elapsedRealtime() - chronometer.base) / 1000).toInt()
            )

        }
    }

    private fun setUpRecycler() = with(binding) {
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), getSpanCount())
        adapter.addImages(defaultImageList)
    }

    private fun getSpanCount() = when (requireArguments().getInt(ITEMS_COUNT)) {
        EASY -> 4
        YOU_CAN_DO_IT -> 6
        HARD -> 8
        else -> 10
    }
}
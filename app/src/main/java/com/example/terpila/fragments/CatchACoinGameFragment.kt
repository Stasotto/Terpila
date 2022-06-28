package com.example.terpila.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.terpila.R
import com.example.terpila.contract.navigator
import com.example.terpila.databinding.FragmentGameCatchCoinBinding
import kotlinx.coroutines.*

class CatchACoinGameFragment : Fragment(R.layout.fragment_game_catch_coin), View.OnTouchListener {

    companion object {
        private const val MOVE_LEFT = 0
        private const val MOVE_RIGHT = 1
        private const val COIN = "coin"
        private const val STONE = "stone"
        private const val SPAWN_DELAY = "Spawn delay"
        private const val DIFFICULTY = "Difficulty"
        const val TAG = "Catch a coin"
        fun newInstance(difficulty: String, spawnCount: Long) = CatchACoinGameFragment().apply {
            arguments = bundleOf(DIFFICULTY to difficulty, SPAWN_DELAY to spawnCount)
        }
    }

    private var currentScore = 0
    private var spawnStateCoin = true
    private var isMovingRight = false
    private var isMovingLeft = false
    private val display = DisplayMetrics()
    private val spawnStateCor by lazy { CoroutineScope(Dispatchers.Main) }
    private val params = ConstraintLayout.LayoutParams(80, 80)
    private val spawnDelay: Long
        get() = requireArguments().getLong(SPAWN_DELAY)
    private val animLeft by lazy {
        getDrawable(
            requireContext(),
            R.drawable.animation_person_left
        ) as AnimationDrawable
    }
    private val animRight by lazy {
        getDrawable(
            requireContext(),
            R.drawable.animation_person
        ) as AnimationDrawable
    }
    private val binding by viewBinding(FragmentGameCatchCoinBinding::bind)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnTouchListener(this)
        requireActivity().windowManager.defaultDisplay.getMetrics(display)
        spawn(
            COIN,
            spawnDelay,
            R.drawable.coin1,
            params
        )
        spawn(
            STONE,
            spawnDelay + 200,
            R.drawable.stone,
            params
        )
    }

    override fun onPause() {
        super.onPause()
        spawnStateCor.cancel()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("MyLog", "touch1")
                if (event.x >= display.widthPixels / 2) {
                    isMovingLeft = false
                    isMovingRight = true
                    movePlayer(animRight, MOVE_RIGHT, R.drawable.default_instance)
                } else {
                    isMovingRight = false
                    isMovingLeft = true
                    movePlayer(animLeft, MOVE_LEFT, R.drawable.default_left)
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d("MyLog", "touch2, ${event.x}")
                if (event.getX(event.actionIndex) >= display.widthPixels / 2) {
                    isMovingLeft = false
                    isMovingRight = true
                    movePlayer(animRight, MOVE_RIGHT, R.drawable.default_instance)
                } else {
                    isMovingRight = false
                    isMovingLeft = true
                    movePlayer(animLeft, MOVE_LEFT, R.drawable.default_left)
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.d("MyLog", "touch Up")
                isMovingLeft = false
                isMovingRight = false
            }
        }
        return true
    }

    private fun spawn(
        tag: String,
        spawnDelay: Long,
        imageResources: Int,
        params: ConstraintLayout.LayoutParams
    ) {
        spawnStateCor.launch {
            while (spawnStateCoin) {
                val obj = ImageView(requireContext())
                obj.tag = tag
                obj.x = (0..display.widthPixels).random().toFloat()
                delay(spawnDelay)
                obj.layoutParams = params
                binding.root.addView(obj)
                obj.setImageResource(imageResources)
                moveObj(obj)
            }
        }
    }

    private fun moveObj(obj: ImageView) {
        spawnStateCor.launch {
            val state = true
            while (state) {
                delay(10)
                obj.y += 6
                if (obj.y >= binding.grass.y) {
                    break
                }
                if (isCaught(obj)) {
                    break
                }
            }
            binding.root.removeView(obj)
        }

    }

    private fun isCaught(obj: ImageView): Boolean = with(binding) {
        if (obj.y in person.y..person.y + person.height && obj.x in person.x..person.x + person.width) {
            when (obj.tag) {
                COIN -> {
                    currentScore++
                    score.text = currentScore.toString()
                }
                STONE -> {
                    root.removeView(obj)
                    dead.isVisible = true
                    openResultFragment()
                    spawnStateCor.cancel()
                }
            }
            return@with true
        } else {
            return@with false
        }
    }

    private fun openResultFragment() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            navigator().showGameResultFragment(
                this::class.java,
                requireArguments().getString(DIFFICULTY)!!,
                currentScore
            )
        }
    }

    private fun movePlayer(anim: AnimationDrawable, moveDirection: Int, defaultImage: Int) =
        with(binding) {
            spawnStateCor.launch {
                person.background = anim
                anim.start()
                person.setImageResource(0)
                when (moveDirection) {
                    MOVE_RIGHT -> {
                        while (isMovingRight) {
                            if (person.x >= display.widthPixels - person.width) break
                            delay(1)
                            person.x += 2
                        }
                    }
                    MOVE_LEFT -> {
                        while (isMovingLeft) {
                            if (person.x <= 0) break
                            delay(1)
                            person.x -= 2
                        }
                    }
                }
                anim.stop()
                person.background = null
                person.setImageResource(defaultImage)
            }
        }
}
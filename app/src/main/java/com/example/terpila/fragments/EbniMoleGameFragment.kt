package com.example.terpila.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.terpila.R
import com.example.terpila.contract.navigator
import com.example.terpila.databinding.FragmentGameEbniMoleBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EbniMoleGameFragment : Fragment(R.layout.fragment_game_ebni_mole) {

    companion object {
        const val TAG = "Ebni Mole"
        private const val DIFFICULTY = "Difficulty"
        private const val MOLE_SPEED = "mole speed"

        fun newInstance(difficulty: String, moleSpeed: Long) = EbniMoleGameFragment().apply {
            arguments = bundleOf(MOLE_SPEED to moleSpeed, DIFFICULTY to difficulty)
        }
    }

    private var currentScore: Int = 0
    private lateinit var timer: CountDownTimer
    private var timerIsRunning = true
    private val binding by viewBinding(FragmentGameEbniMoleBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoleList()
        startTimer()
    }

    override fun onResume() {
        super.onResume()
        timer.start()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    private fun initMoleList() = with(binding) {
        val moleList = listOf(mole1, mole2, mole3, mole4, mole5, mole6, mole7, mole8, mole9)
        startGame(moleList)
    }

    @SuppressLint("SetTextI18n")
    private fun startGame(moleList: List<ImageView>) {
        CoroutineScope(Dispatchers.Main).launch {
            while (timerIsRunning) {
                val item = moleList.random()
                item.isEnabled = true
                item.setOnClickListener {
                    currentScore++
                    binding.score.text = "Score: $currentScore"
                }
                item.setImageResource(R.drawable.mole)
                delay(requireArguments().getLong(MOLE_SPEED))
                item.setImageResource(R.drawable.dirt)
                item.isEnabled = false
            }
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimer(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                updateTimer()
            }
        }
    }

    private fun updateTimer(millisUntilFinished: Long = 0) {
        if (millisUntilFinished > 0)
            binding.timer.text = millisUntilFinished.toString()
        else {
            timerIsRunning = false
            navigator().showGameResultFragment(
                this::class.java, requireArguments().getString(
                    DIFFICULTY
                )!!, currentScore
            )
        }
    }
}
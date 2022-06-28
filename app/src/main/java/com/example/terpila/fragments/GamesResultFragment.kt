package com.example.terpila.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.terpila.R
import com.example.terpila.contract.navigator
import com.example.terpila.databinding.FragmentGamesResultBinding
import com.example.terpila.viewmodels.GamesResultFragViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class GamesResultFragment : Fragment(R.layout.fragment_games_result) {

    private enum class Difficulties {
        EASY,
        YOU_CAN_DO_IT,
        HARD,
        NIGHTMARE
    }

    companion object {
        const val TAG = "GamesResult"
        private const val CURRENT_CLASS_NAME = "Current lass name"
        private const val CURRENT_DIFFICULTY = "Current difficulty"
        private const val CURRENT_SCORE = "Current score"
        fun <T> newInstance(clazz: Class<T>, difficulty: String, score: Int) =
            GamesResultFragment().apply {
                arguments = bundleOf(
                    CURRENT_CLASS_NAME to clazz.name,
                    CURRENT_SCORE to score,
                    CURRENT_DIFFICULTY to difficulty
                )
            }
    }

    private var coins: Int = 0
    private var record: Int = 0
    private var interAd: InterstitialAd? = null
    private val binding by viewBinding(FragmentGamesResultBinding::bind)
    private val viewModel by viewModel<GamesResultFragViewModel>()
    private val interAdCallback = object : InterstitialAdLoadCallback() {

        override fun onAdFailedToLoad(p0: LoadAdError) {
            interAd = null
        }

        override fun onAdLoaded(ad: InterstitialAd) {
            interAd = ad
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MobileAds.initialize(context)
        viewModel.loadCoinsAndRecord(
            requireArguments().getString(CURRENT_CLASS_NAME)!! + getKey(
                requireArguments().getString(
                    CURRENT_DIFFICULTY
                )!!
            )
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
        setUpClickListeners()
        loadInterstitialAd()
    }

    private fun setContent() = with(binding) {
        viewModel.coinsCount.observe(viewLifecycleOwner, { coinsCount ->
            coins = coinsCount.toInt() + requireArguments().getInt(CURRENT_SCORE)
            coinsTv.text = coins.toString()
            viewModel.saveCoinsCount(coins)
        })
        currentScoreText.text = requireArguments().getInt(CURRENT_SCORE).toString()
        viewModel.record.observe(viewLifecycleOwner, {
            record = it.toInt()
            newRecord.visibility =
                if (checkIfCurrentScoreIsNewRecord(requireArguments().getInt(CURRENT_SCORE))) View.VISIBLE
                else View.INVISIBLE
            recordText.text = record.toString()
            saveNewRecord()
        })
    }

    private fun saveNewRecord() {
        if (checkIfCurrentScoreIsNewRecord(requireArguments().getInt(CURRENT_SCORE))) {
            viewModel.saveRecord(
                requireArguments().getString(CURRENT_CLASS_NAME)!! + getKey(
                    requireArguments().getString(
                        CURRENT_DIFFICULTY
                    )!!
                ),
                requireArguments().getInt(CURRENT_SCORE)
            )
        }
    }

    private fun checkIfCurrentScoreIsNewRecord(currentScore: Int): Boolean {
        return currentScore > record
    }

    private fun setUpClickListeners() = with(binding) {
        tryAgain.setOnClickListener {
            navigator().goBack()

        }
        menu.setOnClickListener {
            showInterAd()
        }
    }

    private fun loadInterstitialAd() {
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            interAdCallback
        )
    }

    private fun showInterAd() {
        if (interAd != null) {
            interAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    navigator().goToMenu()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    navigator().goToMenu()
                    interAd = null
                    loadInterstitialAd()
                }

                override fun onAdShowedFullScreenContent() {
                    interAd = null
                    loadInterstitialAd()
                }
            }
            interAd?.show(requireActivity())
        } else {
            navigator().goToMenu()
        }
    }

    private fun getKey(difficulty: String) = when (difficulty) {
        getString(R.string.easy) -> Difficulties.EASY.name
        getString(R.string.you_can_do_it) -> Difficulties.YOU_CAN_DO_IT.name
        getString(R.string.hard) -> Difficulties.HARD.name
        else -> Difficulties.NIGHTMARE.name
    }
}
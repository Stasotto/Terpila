package com.example.terpila.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.terpila.R
import com.example.terpila.adapters.GameAdapter
import com.example.terpila.adapters.ItemGameListener
import com.example.terpila.contract.navigator
import com.example.terpila.databinding.FragmentChooserGameBinding
import com.example.terpila.models.Game
import com.example.terpila.viewmodels.ChooserGameFragViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooserGameFragment : Fragment(R.layout.fragment_chooser_game) {

    companion object {
        private const val AD1_COST = 0
        private const val AD2_COST = 500
        const val TAG = "ChooserGame"
        fun newInstance() = ChooserGameFragment()

    }

    private val binding by viewBinding(FragmentChooserGameBinding::bind)
    private val adapter by lazy { GameAdapter(itemGameListener) }
    private val viewModel by viewModel<ChooserGameFragViewModel>()
    private var rewardedAd: RewardedAd? = null
    private var coins = 0

    private val itemGameListener = object : ItemGameListener {
        override fun onPlayButtonClickListener(game: Game) {
            when (game.id) {
                1 -> navigator().showEbniMoleGameFragment(
                    game.levels[game.currentDifficulty].text,
                    game.levels[game.currentDifficulty].value.toLong())
                2 -> navigator().showCatchACoinGameFragment(
                    game.levels[game.currentDifficulty].text,
                    game.levels[game.currentDifficulty].value.toLong()
                )
                3 -> navigator().showFindACoupleGameFragment(
                    game.levels[game.currentDifficulty].text,
                    game.levels[game.currentDifficulty].value
                )
            }
        }

        override fun onDifficultyChangedListener(newDifficulty: Int, game: Game) {
            viewModel.changeDifficultyOfGame(newDifficulty, game)
        }
    }

    private val rewardedCallBack = object : RewardedAdLoadCallback() {
        override fun onAdFailedToLoad(p0: LoadAdError) {
            rewardedAd = null
        }

        override fun onAdLoaded(ad: RewardedAd) {
            rewardedAd = ad
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MobileAds.initialize(context)
        loadRewordedAd()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpClickListeners()
        viewModel.coins.observe(viewLifecycleOwner, {
            coins = it

        })
    }

    private fun setUpClickListeners() = with(binding) {
        btnAds1.setOnClickListener {
            buyAd(AD1_COST, 5)
        }
        btnAds2.setOnClickListener {
            buyAd(AD2_COST, 10)
        }
    }

    private fun buyAd(adCost: Int, xAmount: Int) {
        if (coins >= adCost) {
            coins -= adCost
            viewModel.saveCoins(coins)
            showRewardedAd(xAmount)
        } else {
            Toast.makeText(requireContext(), "Не хватает монет", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setUpRecycler() = with(binding) {
        recyclerOfGames.adapter = adapter
        recyclerOfGames.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val itemAnimator = recyclerOfGames.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }
        observeGames()
    }

    private fun observeGames() {
        viewModel.games.observe(viewLifecycleOwner, { gameList ->
            adapter.addGames(gameList)
            Log.d("MyLog", "changed")
        })
    }

    private fun loadRewordedAd() {
        RewardedAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build(),
            rewardedCallBack
        )
    }

    private fun showRewardedAd(xAmount: Int) {
        if (rewardedAd != null) {
            rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    rewardedAd = null
                    loadRewordedAd()
                }

                override fun onAdDismissedFullScreenContent() {
                    rewardedAd = null
                    loadRewordedAd()
                }
            }
            rewardedAd?.show(requireActivity()) {
                navigator().showRewardDialogFragment(xAmount * it.amount)
            }
        }
    }
}
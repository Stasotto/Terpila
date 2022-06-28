package com.example.terpila.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.terpila.R
import com.example.terpila.contract.navigator
import com.example.terpila.databinding.FragmentMenuBinding
import com.example.terpila.viewmodels.MenuFragViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {

    companion object {
        const val TAG = "Menu"
        fun newInstance() = MenuFragment()
    }


    private val binding by viewBinding(FragmentMenuBinding::bind)
    private val viewModel: MenuFragViewModel by viewModel()
    private var levelOfTerpila = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MobileAds.initialize(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateData()
        updateUi()
        setUpListeners()
    }

    override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.adView.pause()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.adView.destroy()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("MyLog", "onDetach")
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi() = with(binding) {
        viewModel.level.observe(viewLifecycleOwner, {level ->
            levelOfTerpila = level
            levelTv.text = level.toString()
        })

        viewModel.coinsCount.observe(viewLifecycleOwner, { coins ->
            coinsTv.text = coins.toString()
            Log.d("MyLogProggres", coins.toString())
        })

        viewModel.progress.observe(viewLifecycleOwner, {progress ->
            progressPb.progress = progress
            progressPb.max = if(levelOfTerpila == 0) 1 * 100 else levelOfTerpila * 100
            progressTv.text = "${progressPb.progress}/${progressPb.max}"
            Log.d("MyLog", progress.toString())
        })
    }

    private fun setUpListeners() = with(binding) {
        exitBtn.setOnClickListener { navigator().exit() }
        settingsBtn.setOnClickListener {
            navigator().showSettingsFragment()
        }
        terpetBtn.setOnClickListener {
            navigator().showChooserGameFragment()
        }
    }
}
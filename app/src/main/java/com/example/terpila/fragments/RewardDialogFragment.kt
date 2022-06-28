package com.example.terpila.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.terpila.databinding.FragmentDialogRewardBinding
import com.example.terpila.viewmodels.RewardDialogFragViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RewardDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "Reward dialog fragment"
        private const val KEY_PROGRESS_TO_ADD = "progress to add"

        fun newInstance(progressToAdd: Int) = RewardDialogFragment().apply {
            arguments = bundleOf(KEY_PROGRESS_TO_ADD to progressToAdd)
        }
    }

    private var progressToAdd = 0
    private var levelOfTerpila = 0
    private val binding by lazy { FragmentDialogRewardBinding.inflate(layoutInflater) }
    private val mainScope by lazy { CoroutineScope(Dispatchers.Main) }
    private val viewModel by viewModel<RewardDialogFragViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressToAdd = requireArguments().getInt(KEY_PROGRESS_TO_ADD)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Reward")
            .setView(binding.root)
            .setPositiveButton("Хорошо", null)
            .create()
    }

    override fun onStart() {
        super.onStart()
        updateUi()
    }

    override fun onResume() {
        super.onResume()
        setProgress()
    }

    @SuppressLint("SetTextI18n")
    private fun setProgress() = with(binding) {
        mainScope.launch {
            while (progressToAdd != 0) {
                progressPb.progress++
                progressTv.text = "${progressPb.progress}/${progressPb.max}"
                isNewLevel()
                progressToAdd--
                delay(50)
            }
            viewModel.saveProgress(binding.progressPb.progress)
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun isNewLevel() = with(binding) {
        if (progressPb.progress == progressPb.max) {
            levelTv.text = (++levelOfTerpila).toString()
            viewModel.saveLevel(levelOfTerpila)
            progressPb.progress = 0
            progressPb.max += 100
            progressTv.text = "${progressPb.progress}/${progressPb.max}"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi() = with(binding) {

        viewModel.level.observe(this@RewardDialogFragment, { level ->
            levelOfTerpila = level
            levelTv.text = level.toString()
        })
        viewModel.progress.observe(this@RewardDialogFragment, { progress ->
            progressPb.progress = progress
            progressPb.max = if (levelOfTerpila == 0) 1 * 100 else levelOfTerpila * 100
            progressTv.text = "${progressPb.progress}/${progressPb.max}"
            Log.d("MyLog", progress.toString())
        })
    }
}
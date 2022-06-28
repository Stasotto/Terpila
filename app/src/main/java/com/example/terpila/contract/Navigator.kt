package com.example.terpila.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator() = requireActivity() as Navigator

interface Navigator {

    fun showChooserGameFragment()

    fun showCatchACoinGameFragment(difficulty: String, spawnDelay: Long)

    fun showFindACoupleGameFragment(difficulty: String, itemsCount: Int)

    fun showRewardDialogFragment(progress: Int)

    fun showSettingsFragment()

    fun showEbniMoleGameFragment(difficulty: String, moleSpeed: Long)

    fun <T> showGameResultFragment(clazz: Class<T>, difficulty: String, score: Int)

    fun goToMenu()

    fun exit()

    fun goBack()

    fun <T> publishResult(key: String, result: T)

    fun <T> listenResult(key: String, owner: LifecycleOwner, listener: ResultListener<T>)
}
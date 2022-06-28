package com.example.terpila

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.terpila.contract.Navigator
import com.example.terpila.contract.ResultListener
import com.example.terpila.contract.showToast
import com.example.terpila.fragments.*
import com.example.terpila.fragments.findacouplegame.FindACoupleGameFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    companion object {
        private const val RESULT = "Result"
    }

    private var backPressed: Long = 0

    private val fragmentLifecycleListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentAttached(fm, f, context)
            Log.d("MyLogFragment", "onAttache")
        }

        override fun onFragmentCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentCreated(fm, f, savedInstanceState)
            Log.d("MyLogFragment", "onCreate")
        }

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            Log.d("MyLogFragment", "onViewCreated")
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            super.onFragmentStarted(fm, f)
            Log.d("MyLogFragment", "onStart")
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            super.onFragmentResumed(fm, f)
            Log.d("MyLogFragment", "onResume")
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            super.onFragmentStopped(fm, f)
            Log.d("MyLogFragment", "onStop")
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentViewDestroyed(fm, f)
            Log.d("MyLogFragment", "onViewDestroyed")
        }

        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentDestroyed(fm, f)
            Log.d("MyLogFragment", "onDestroy")
        }

        override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
            super.onFragmentDetached(fm, f)
            Log.d("MyLogFragment", "onDetached")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openMenuFragment()
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleListener, false)
    }

    override fun onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            finish()
        } else {
            showToast("Press once again to exit")
        }
        backPressed = System.currentTimeMillis()
    }

    override fun showChooserGameFragment() {
        launchFragment(ChooserGameFragment.newInstance(), ChooserGameFragment.TAG)
    }

    override fun showCatchACoinGameFragment(difficulty: String, spawnDelay: Long) {
        launchFragment(
            CatchACoinGameFragment.newInstance(difficulty, spawnDelay),
            CatchACoinGameFragment.TAG
        )
    }

    override fun showFindACoupleGameFragment(difficulty: String, itemsCount: Int) {
        launchFragment(
            FindACoupleGameFragment.newInstance(difficulty, itemsCount),
            FindACoupleGameFragment.TAG
        )
    }

    override fun showRewardDialogFragment(progress: Int) {
        RewardDialogFragment.newInstance(progress)
            .show(supportFragmentManager, RewardDialogFragment.TAG)
    }

    override fun showSettingsFragment() {
        Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
    }

    override fun showEbniMoleGameFragment(difficulty: String, moleSpeed: Long) {
        launchFragment(
            EbniMoleGameFragment.newInstance(difficulty, moleSpeed),
            EbniMoleGameFragment.TAG
        )
    }

    override fun <T> showGameResultFragment(clazz: Class<T>, difficulty: String, score: Int) {
        launchFragment(
            GamesResultFragment.newInstance(clazz, difficulty, score),
            GamesResultFragment.TAG
        )
    }

    override fun goToMenu() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun exit() {
        finish()
    }

    override fun goBack() {
        super.onBackPressed()
    }

    override fun <T> publishResult(key: String, result: T) {
        supportFragmentManager.setFragmentResult(key, bundleOf(RESULT to result))
    }

    @Suppress("UNCHECKED CAST")
    override fun <T> listenResult(
        key: String,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(
            key,
            owner,
            FragmentResultListener { _, bundle ->
                listener.invoke(bundle.get(RESULT) as T)
            })
    }

    private fun launchFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .addToBackStack(tag)
            .replace(R.id.main_container, fragment, tag)
            .commit()
    }

    private fun openMenuFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MenuFragment.newInstance(), MenuFragment.TAG)
            .commit()
    }
}
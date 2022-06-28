package com.example.terpila.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout

class CheckableLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    ConstraintLayout(context, attr, defStyleAttr, defStyleRes), Checkable {

    private val checkableChild: Checkable by lazy { findCheckableChild(this) }

    private fun findCheckableChild(root: ViewGroup): Checkable {
        for (i in 0 until root.childCount) {
            val child = root.getChildAt(i)
            if (child is Checkable) return child
            if (child is ViewGroup) return findCheckableChild(child)
        }
        throw IllegalStateException("Can't find CheckableChild")
    }


    override fun setChecked(checked: Boolean) {
        checkableChild.isChecked = checked
    }

    override fun isChecked(): Boolean {
        return checkableChild.isChecked
    }

    override fun toggle() {
        checkableChild.toggle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val checkableView = checkableChild as View
        checkableView.isFocusableInTouchMode = false
        checkableView.isFocusable = false
        checkableView.isClickable = false
    }
}
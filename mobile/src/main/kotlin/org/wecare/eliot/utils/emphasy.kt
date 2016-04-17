package org.wecare.eliot.utils

import android.view.View

fun View.FadeScaleHide(duration: Long = 500L, startDelay: Long = 0) {
    this.animate()
            .scaleX(0f)
            .scaleY(0f)
            .alpha(0f)
            .setStartDelay(startDelay)
            .setDuration(duration)
            .withEndAction {
                visibility = View.INVISIBLE
            }
}

fun View.FadeScaleShow(duration: Long = 500L, startDelay: Long = 0) {
    this.alpha = 1f
    this.visibility = View.VISIBLE
    this.animate()
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1f)
            .setDuration(duration)
            .setStartDelay(startDelay)
            .withEndAction {
                visibility = View.VISIBLE
            }
}
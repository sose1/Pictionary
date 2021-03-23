package pl.sose1.ui.extensions

import android.view.View
import android.view.animation.Animation
import java.util.*


fun createAnimation(animStart: Animation, animEnd: Animation?, startOffset: Long, view: View, doSomething: () -> Unit) {
    animStart.startOffset = startOffset
    animStart.setCompletedListener {
        if (animEnd != null) {
            animEnd.setCompletedListener {
                doSomething()
            }
            view.startAnimation(animEnd)
        } else {
            doSomething()
        }
    }
    view.clearAnimation()
    view.startAnimation(animStart)
}

fun Animation.setCompletedListener(onCompleted: () -> Unit) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            animation?.cancel()
            this@setCompletedListener.cancel()
            onCompleted()
        }
        override fun onAnimationRepeat(animation: Animation?) {}
    })
}
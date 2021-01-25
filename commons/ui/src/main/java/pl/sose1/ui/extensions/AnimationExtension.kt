package pl.sose1.ui.extensions

import android.view.View
import android.view.animation.Animation


fun createAnimation(animEnter: Animation, animEnd: Animation?, startOffset: Long, view: View, doSomething: () -> Unit) {
    animEnter.startOffset = startOffset
    animEnter.setCompletedListener {
        if (animEnd != null){
            view.startAnimation(animEnd)
        }
    }
    view.startAnimation(animEnter)
    doSomething()
}

fun Animation.setCompletedListener(onCompleted: () -> Unit) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            onCompleted()
            this@setCompletedListener.cancel()
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    })
}
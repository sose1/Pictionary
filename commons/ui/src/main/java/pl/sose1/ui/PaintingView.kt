package pl.sose1.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import timber.log.Timber
import kotlin.math.abs

class PaintingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    // TODO: 16.12.2020
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var bitmap: Bitmap? = null
    private lateinit var bitmapCanvas: Canvas
    private lateinit var scaledBitmap: Bitmap

    private var path = Path()

    private var paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f
    }

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f


    fun initialize() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap?.let {
            bitmapCanvas = Canvas(it)
        }
        bitmapCanvas.drawColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas?) {
        bitmap?.let { canvas?.drawBitmap(it, 0f, 0f, null) }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        Timber.d("X: $motionTouchEventX, Y: $motionTouchEventY")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to cache it.
            bitmapCanvas.drawPath(path, paint)
        }
        invalidate()
    }

    private fun touchUp() {
        path.reset()
    }

    fun changeBrushColor(color: Int) {
        paint.color = color
    }
}
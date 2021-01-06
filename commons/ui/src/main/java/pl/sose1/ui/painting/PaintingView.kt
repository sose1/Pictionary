package pl.sose1.ui.painting

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import kotlin.math.abs

class PaintingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f
    private var path = Path()

    private val touchTolerance = ViewConfiguration
            .get(context)
            .scaledTouchSlop

    private lateinit var bitmapCanvas: Canvas
    lateinit var bitmap: Bitmap

    var isStarted = false
    var drawnListener: PathDrawnListener? = null

    private var paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f
    }

    fun initialize() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmapCanvas = Canvas(bitmap)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        drawnListener = null
    }

    override fun onDraw(canvas: Canvas?) {
        bitmap.let {
            canvas?.drawBitmap(it, 0f, 0f, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        if (isStarted) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> touchStart()
                MotionEvent.ACTION_MOVE -> touchMove()
                MotionEvent.ACTION_UP -> touchUp()
            }
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
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2,
                    (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            // Draw the path in the extra bitmap to cache it.
            bitmapCanvas.drawPath(path, paint)
        }
        invalidate()
    }


    private fun touchUp() {
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        b.eraseColor(Color.TRANSPARENT)

        val c = Canvas(b)
        c.drawPath(path, paint)

        val s = Bitmap.createScaledBitmap(b, 800, 800, true)

        drawnListener?.onPathDrawn(s)
        path.reset()
    }

    fun changeBrushColor(color: Int) {
        paint.color = color
    }

    fun drawBitmap(byteArray: ByteArray) {
        val immutableBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        val copy = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true)
        bitmap = Bitmap.createScaledBitmap(copy, width, height, true)
        bitmapCanvas = Canvas(bitmap)

        invalidate()
    }

}
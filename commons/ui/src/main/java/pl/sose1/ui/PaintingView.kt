package pl.sose1.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.ByteArrayOutputStream
import kotlin.math.abs

class PaintingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val messageChannel = Channel<ByteArray>(Channel.BUFFERED)

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var bitmap: Bitmap? = null
    private lateinit var bitmapCanvas: Canvas

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

    private var scaledBitmap: Bitmap? = null

    fun initialize() {
        bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        bitmap?.let {
            scaledBitmap = Bitmap.createScaledBitmap(it, width, height, true)
            scaledBitmap?.let { it1 ->
                bitmapCanvas = Canvas(it1)
            }
        }
        bitmapCanvas.drawColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas?) {

        scaledBitmap?.let {
            canvas?.drawBitmap(it, 0f, 0f, null)
            GlobalScope.launch {
                val stream = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                messageChannel.send(byteArray)
            }
        }
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

    fun drawBitmap(byteArray: ByteArray) {
//        val immutableBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//        bitmap = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true)
//        bitmap?.let {
//            scaledBitmap = Bitmap.createScaledBitmap(it, width, height, true)
//            scaledBitmap?.let { it1 ->
//                bitmapCanvas = Canvas(it1)
//            }
//        }
//        invalidate()
    }
}
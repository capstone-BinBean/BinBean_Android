package com.binbean.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.binbean.domain.cafe.FloorListDto
import com.binbean.domain.cafe.FloorPlanResponse
import com.binbean.domain.cafe.PositionDto

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    enum class Mode {
        USER, ADMIN
    }
    var mode: Mode = Mode.USER

    private val gridSize = 50f
    private val gridPaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 1f
    }

    private var offsetX = 0f
    private var lastX = 0f
    private var isScrolling = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(offsetX, 0f)
        drawGrid(canvas)
        canvas.restore()
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate(offsetX, 0f)
        drawGrid(canvas)
        canvas.restore()
        super.dispatchDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                isScrolling = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isScrolling) {
                    val dx = event.x - lastX
                    offsetX += dx
                    lastX = event.x
                    invalidate()
                    updateObjectTranslation()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isScrolling = false
            }
        }
        return true
    }

    private fun drawGrid(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()
        for (x in 0..(width / gridSize).toInt()) {
            canvas.drawLine(x * gridSize, 0f, x * gridSize, height, gridPaint)
        }
        for (y in 0..(height / gridSize).toInt()) {
            canvas.drawLine(0f, y * gridSize, width, y * gridSize, gridPaint)
        }
    }

    private fun updateObjectTranslation() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.translationX = offsetX
        }
    }

    fun getOffsetX(): Float = offsetX

    fun renderFloorPlan(data: FloorListDto, currentSeats: List<PositionDto>) {
        removeAllViews()

        data.seatPosition.forEach {
            val isOccupied = currentSeats.any { seat -> seat.x == it.x && seat.y == it.y }
            addSeat(it.x, it.y, isOccupied)
        }
        data.doorPosition.forEach { addObjectAt(it.x, it.y, R.drawable.obj_door, "seat_${it.x}_${it.y}") }
        data.counterPosition.forEach { addObjectAt(it.x, it.y, R.drawable.obj_casher, "seat_${it.x}_${it.y}") }
        data.toiletPosition.forEach { addObjectAt(it.x, it.y, R.drawable.obj_toilet, "seat_${it.x}_${it.y}") }
        data.windowPosition.forEach { addObjectAt(it.x, it.y, R.drawable.obj_window, "seat_${it.x}_${it.y}") }
    }

    private fun addObjectAt(x: Float, y: Float, drawableResId: Int, tag: String? = null) {
        val imageView = ImageView(context).apply {
            setImageResource(drawableResId)
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.x = x * gridSize
            this.y = y * gridSize
            this.tag = tag
        }
        addView(imageView)
    }

    private fun addSeat(x: Float, y: Float, occupied: Boolean) {
        val view = ImageView(context).apply {
            setImageResource(R.drawable.obj_seat)
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.x = x * gridSize
            this.y = y * gridSize

            if (occupied) {
                val green = ContextCompat.getColor(context, R.color.main_green)
                setColorFilter(green)
            }
        }
        addView(view)
    }


}
package com.binbean.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class CanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

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
}
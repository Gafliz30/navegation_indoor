package com.example.pucmm_map

import android.graphics.Matrix
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView

class ImageHandler(private val imageView: ImageView) {

    private var matrix = Matrix()
    private var scaleFactor = 1.0f
    private var lastFocusX = 0f
    private var lastFocusY = 0f
    private val scaleGestureDetector: ScaleGestureDetector

    init {
        scaleGestureDetector = ScaleGestureDetector(imageView.context, ScaleListener())
        imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    lastFocusX = event.x
                    lastFocusY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val focusDeltaX = event.x - lastFocusX
                    val focusDeltaY = event.y - lastFocusY
                    lastFocusX = event.x
                    lastFocusY = event.y
                    matrix.postTranslate(focusDeltaX, focusDeltaY)
                    imageView.imageMatrix = matrix
                }
            }
            true
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = scaleFactor.coerceIn(0.1f, 5.0f)
            matrix.setScale(scaleFactor, scaleFactor)
            imageView.imageMatrix = matrix
            return true
        }
    }
}

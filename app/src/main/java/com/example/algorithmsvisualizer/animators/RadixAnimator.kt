package com.example.algorithmsvisualizer.animators

import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import com.example.algorithmsvisualizer.algorithms.RadixSort
import com.example.algorithmsvisualizer.views.Radix
import kotlin.math.*

class RadixAnimator(val sortObj: RadixSort) {
    var angle = 0f
    var baseIndicesSize = MutableList(10) { 1 }


    fun translateToBase(baseIndex: Int, radix: Radix) {
        val compareRectLeft = sortObj.radixView.baseRects[baseIndex].left
        val marginY = 20f
        val compareRectBottom =
            sortObj.radixView.baseRects[baseIndex].bottom - (baseIndicesSize[baseIndex] * (sortObj.radixView.baseRects[baseIndex].height() + marginY))
        val relativeX = compareRectLeft - radix.left
        val relativeY = compareRectBottom - radix.bottom
        angle = atan(relativeX / relativeY)
        val finalRadius = sqrt(relativeX.pow(2) + relativeY.pow(2))
        val initialX = radix.left
        val initialY = radix.bottom


        ValueAnimator.ofFloat(0f, finalRadius).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
            addUpdateListener {
                val left =
                    radialToCartesian(it.animatedValue as Float, true, initialX, initialY).first
                val bottom =
                    radialToCartesian(it.animatedValue as Float, true, initialX, initialY).second
                radix.move(left,bottom)
                sortObj.invalidate()
            }
            addListener({
                sortObj.sortedList[baseIndex].add(radix)
                baseIndicesSize[baseIndex]++
                sortObj.sortNextItem()
            })

        }.start()
    }

    fun translateToGrid(index:Int,radix:Radix){
        val compareRectLeft=radix.getBoxLeftFromIndex(index)
        val compareRectBottom=radix.getBoxBottomFromIndex(index)
        val relativeX=compareRectLeft-radix.left
        val relativeY=compareRectBottom-radix.bottom
        angle = atan(relativeX / relativeY)
        val finalRadius = sqrt(relativeX.pow(2) + relativeY.pow(2))
        val initialX = radix.left
        val initialY = radix.bottom

        ValueAnimator.ofFloat(0f, finalRadius).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
            addUpdateListener {
                val left =
                    radialToCartesian(it.animatedValue as Float, false, initialX, initialY).first
                val bottom =
                    radialToCartesian(it.animatedValue as Float, false, initialX, initialY).second
                radix.move(left,bottom)
                sortObj.invalidate()
            }
            addListener({
               sortObj.moveUpNextElement()
            })

        }.start()
    }





    private fun radialToCartesian(
        radius: Float,
        isItDownWord: Boolean,
        initialX: Float,
        initialY: Float
    ): Pair<Float, Float> {
        var x = 0f
        var y = 0f
        if (isItDownWord) {
            x = initialX + (radius * sin(angle))
            y = initialY + (radius * cos(angle))
        } else {
            x = initialX - (radius * sin(angle))
            y = initialY - (radius * cos(angle))
        }
        return Pair(x, y)
    }
}
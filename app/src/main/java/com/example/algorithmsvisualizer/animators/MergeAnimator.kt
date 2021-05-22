package com.example.algorithmsvisualizer.animators

import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import com.example.algorithmsvisualizer.algorithms.MergeSort
import com.example.algorithmsvisualizer.views.GraphBar
import kotlin.math.*

class MergeAnimator(val sortObj: MergeSort) {

    private var angle: Float = 0f

    fun moveBarToComparePosition(animatedBar: GraphBar, index: Int) {
        val compareYPosition = 600f
        val compareXPosition = sortObj.graphView.getBarCenterPositionFromIndex(index)

        val relativeX = compareXPosition - animatedBar.center
        val relativeY = compareYPosition
        angle = atan(relativeX / relativeY)
        val finalRadius = sqrt(relativeX.pow(2) + relativeY.pow(2))
        val initialX = animatedBar.center
        val initialY = animatedBar.top


        ValueAnimator.ofFloat(0f, finalRadius).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
            addUpdateListener {
                val x = radialToCartesian(it.animatedValue as Float, true, initialX, initialY).first
                val y =
                    radialToCartesian(it.animatedValue as Float, true, initialX, initialY).second
                animatedBar.moveHorizontalAbs(x)
                animatedBar.moveVertical(y)
                sortObj.invalidate()
            }
            addListener({
                sortObj.mergeSwap()
            })

        }.start()


    }

    fun moveSortedBarsUp(list: MutableList<GraphBar>, lastOrderedElementIndex:Int) {

        list.forEachIndexed { index, graphBar ->
            val barIndex = index + lastOrderedElementIndex
            val positionX = sortObj.graphView.getBarCenterPositionFromIndex(barIndex)
            val positionY = sortObj.graphView.barsDefaultTop
            val relativeX = positionX - graphBar.center
            val relativeY = positionY - graphBar.top
            angle = atan(relativeX / relativeY)
            val finalRadius = sqrt(relativeX.pow(2) + relativeY.pow(2))
            val initialX = graphBar.center
            val initialY = graphBar.top

            ValueAnimator.ofFloat(0f, finalRadius).apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 500

                addUpdateListener { valueAnimator ->
                    val x = radialToCartesian(
                        valueAnimator.animatedValue as Float,
                        false,
                        initialX,
                        initialY
                    ).first
                    val y = radialToCartesian(
                        valueAnimator.animatedValue as Float,
                        false,
                        initialX,
                        initialY
                    ).second

                    graphBar.moveHorizontalAbs(x)
                    graphBar.moveVertical(y)
                    sortObj.invalidate()
                }
                addListener({
                    //onEnd Animation Listener
                    if (index == list.size - 1) sortObj.sort()
                })


            }.start()
        }


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
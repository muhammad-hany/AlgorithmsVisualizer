package com.example.algorithmsvisualizer.animators

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.animation.LinearInterpolator
import com.example.algorithmsvisualizer.algorithms.BubbleSort
import com.example.algorithmsvisualizer.views.GraphBar

class BubbleAnimator(private val sortObj: BubbleSort) {


    fun compareStartAnimation(index1: Int, index2: Int) {
        val bar1 = sortObj.barsList[index1]
        val bar2 = sortObj.barsList[index2]

        val distanceToMove = 30f
        val endPosition = bar1.top + distanceToMove
        ValueAnimator.ofArgb(GraphBar.barDefaultColor, sortObj.compareColor).apply {
            duration = 20
            interpolator = LinearInterpolator()
            addUpdateListener {
                bar1.paint.color = it.animatedValue as Int
                bar2.paint.color = it.animatedValue as Int
                sortObj.invalidate()

                if (bar1.paint.color == sortObj.compareColor) {
                    if (sortObj.hasSwapAction) swapAnimation(
                        index1,
                        index2
                    ) else compareEndAnimation(index1, index2)
                }
            }
        }.start()

    }


    private fun swapAnimation(index1: Int, index2: Int) {

        val bar1 = sortObj.barsList[index1]
        val bar2 = sortObj.barsList[index2]
        val endPosition = bar2.center
        //compensationFactor to compensate the start position of bar2
        val compensationFactor = bar1.center + bar2.center

        ValueAnimator.ofFloat(bar1.center, bar2.center).apply {
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
                bar1.moveHorizontalAbs(it.animatedValue as Float)
                bar2.moveHorizontalAbs(compensationFactor - it.animatedValue as Float)
                sortObj.invalidate()
                if (bar1.center == endPosition) {
                    compareEndAnimation(index1, index2)

                }
            }
        }.start()
    }

    private fun compareEndAnimation(index1: Int, index2: Int) {
        val bar1 = sortObj.barsList[index1]
        val bar2 = sortObj.barsList[index2]
        val distanceToMove = -30f
        val endPosition = bar1.top + distanceToMove

        val compareColor = Color.argb(255, 100, 100, 100)
        ValueAnimator.ofArgb(sortObj.compareColor, GraphBar.barDefaultColor).apply {
            duration = 20
            interpolator = LinearInterpolator()
            addUpdateListener {
                bar1.paint.color = it.animatedValue as Int
                bar2.paint.color = it.animatedValue as Int
                sortObj.invalidate()

                if (bar1.paint.color == GraphBar.barDefaultColor) {
                    sortObj.onStepFinish()
                }
            }
        }.start()

    }


}


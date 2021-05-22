package com.example.algorithmsvisualizer.animators

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.animation.LinearInterpolator
import com.example.algorithmsvisualizer.algorithms.SelectionSort
import com.example.algorithmsvisualizer.views.GraphBar

class SelectionAnimator(val sortObj: SelectionSort) {

    fun compareStartAnimation(index1: Int, index2: Int) {
        val bar1 = sortObj.barsList[index1]
        val bar2 = sortObj.barsList[index2]

        ValueAnimator.ofArgb(GraphBar.barDefaultColor, sortObj.compareColor).apply {
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
                bar1.paint.color = it.animatedValue as Int
                bar2.paint.color = it.animatedValue as Int
                sortObj.invalidate()

                if (bar1.paint.color == sortObj.compareColor) {
                    compareEndAnimation(index1, index2)

                }
            }
        }.start()
    }

    fun compareEndAnimation(index1: Int, index2: Int) {
        val bar1 = sortObj.barsList[index1]
        val bar2 = sortObj.barsList[index2]

        ValueAnimator.ofArgb(sortObj.compareColor, GraphBar.barDefaultColor).apply {
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
                bar1.paint.color = it.animatedValue as Int
                bar2.paint.color = it.animatedValue as Int
                sortObj.invalidate()

                if (bar1.paint.color == GraphBar.barDefaultColor) {
                    val itHasSwapAction=sortObj.swapSelection != sortObj.iCounter
                    if (itHasSwapAction) {
                        sortObj.removeAnyRedColor()
                        sortObj.barsList[sortObj.swapSelection].paint.color =
                            Color.RED
                    }
                    sortObj.jCounter++
                    sortObj.sort()


                }
            }
        }.start()
    }

    fun swapAnimation(index1: Int, index2: Int) {
        val bar1 = sortObj.barsList[index1]
        val bar2 = sortObj.barsList[index2]
        val endPosition = bar2.center
        //compensationFactor to compensate the start position of bar2
        val compensationFactor = bar1.center + bar2.center

        ValueAnimator.ofFloat(bar1.center, bar2.center).apply {
            /*setFloatValues()*/
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
                bar1.moveHorizontalAbs(it.animatedValue as Float)
                bar2.moveHorizontalAbs(compensationFactor - it.animatedValue as Float)
                sortObj.invalidate()
                if (bar1.center == endPosition) {
                    sortObj.onSwapFinish()

                }
            }
        }.start()
    }


}
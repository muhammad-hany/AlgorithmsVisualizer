package com.example.algorithmsvisualizer.animators

import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.example.algorithmsvisualizer.algorithms.QuickSort
import com.example.algorithmsvisualizer.views.GraphBar

class QuickAnimator(private val sortObj: QuickSort) {


    fun compareStartAnimation(pivotIndex: Int, comparedBarIndex: Int, pivotInLeftSide: Boolean) {
        val pivot = sortObj.sortList[pivotIndex]
        val comparedBar = sortObj.sortList[comparedBarIndex]
        ValueAnimator.ofArgb(pivot.paint.color, sortObj.compareColor).apply {
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
               // pivot.paint.color = it.animatedValue as Int
                comparedBar.paint.color = it.animatedValue as Int
                sortObj.invalidate()
            }

            addListener({
                if (pivotInLeftSide) {
                    sortObj.comparePivotWithJCounter()
                } else {
                    sortObj.comparePivotWithICounter()
                }
            })
        }.start()
    }


    fun compareSwapAnimation(pivotIndex: Int, comparedBarIndex: Int) {
        val pivot = sortObj.sortList[pivotIndex]
        val comparedBar = sortObj.sortList[comparedBarIndex]
        val compensationFactor = pivot.center + comparedBar.center
        ValueAnimator.ofFloat(pivot.center, comparedBar.center).apply {
            duration = 200
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                pivot.moveHorizontalAbs(it.animatedValue as Float)
                comparedBar.moveHorizontalAbs(compensationFactor - it.animatedValue as Float)
                sortObj.invalidate()
            }

            addListener({
                sortObj.swap(pivotIndex, comparedBarIndex)

                compareEndAnimation(pivotIndex = comparedBarIndex,pivotIndex)
            })
        }.start()
    }


    fun compareEndAnimation(pivotIndex: Int, bar2Index: Int) {
        val bar1 = sortObj.sortList[pivotIndex]
        val bar2 = sortObj.sortList[bar2Index]
        ValueAnimator.ofArgb(bar1.paint.color, bar2.lastPaint.color).apply {
            duration = 500
            interpolator = LinearInterpolator()
            addUpdateListener {
               // bar1.paint.color = it.animatedValue as Int
                bar2.paint.color = it.animatedValue as Int
                sortObj.invalidate()
            }

            addListener({
                sortObj.sort()
            })
        }.start()
    }
}
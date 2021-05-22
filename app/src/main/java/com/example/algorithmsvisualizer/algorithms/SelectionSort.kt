package com.example.algorithmsvisualizer.algorithms

import android.graphics.Color
import com.example.algorithmsvisualizer.animators.SelectionAnimator
import com.example.algorithmsvisualizer.views.GraphBar
import com.example.algorithmsvisualizer.views.GraphView

class SelectionSort(graphView: GraphView) : SortAlgorithm(graphView) {

    private val animator: SelectionAnimator = SelectionAnimator(this)

    init {
        iCounter = 0
        jCounter = iCounter + 1
    }

    private var hasSwapAction = false
    var swapSelection = iCounter
    override fun sort() {
        if (animationState != ON_RESUME) return
        if (iCounter < barsList.size - 1) {

            if (jCounter < barsList.size) {
                animator.compareStartAnimation(iCounter, jCounter)

                if (barsList[jCounter] < barsList[swapSelection]) {

                    swapSelection = jCounter
                    hasSwapAction = true
                }

            } else {
                if (hasSwapAction) {
                    animator.swapAnimation(iCounter, swapSelection)
                } else {
                    onStepFinish()
                }

            }
        } else {
            barsList[iCounter].paint.color=comparedColor
            onFinishSorting()
        }


    }

    private fun onStepFinish() {
        barsList[iCounter].paint.color = comparedColor
        incrementOnStepFinish()
        removeAnyRedColor()
        sort()
    }

    private fun incrementOnStepFinish() {
        iCounter++
        jCounter = iCounter + 1
        swapSelection = iCounter
        hasSwapAction = false
    }

    fun removeAnyRedColor() {
        barsList.filter { it.paint.color == Color.RED }
            .map { it.paint.color = GraphBar.barDefaultColor }
        invalidate()
    }

    fun onSwapFinish() {
        invalidate()
        swap(iCounter, swapSelection)
        onStepFinish()
    }

    override fun onStartSorting() {
        iCounter = 0
        jCounter = iCounter + 1
        swapSelection = iCounter
        animationState = ON_RESUME
        sort()
    }


}
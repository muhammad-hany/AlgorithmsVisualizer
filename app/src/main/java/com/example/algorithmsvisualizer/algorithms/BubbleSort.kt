package com.example.algorithmsvisualizer.algorithms

import android.graphics.Color
import com.example.algorithmsvisualizer.*
import com.example.algorithmsvisualizer.animators.BubbleAnimator
import com.example.algorithmsvisualizer.views.GraphBar
import com.example.algorithmsvisualizer.views.GraphView


class BubbleSort(graphView: GraphView) : SortAlgorithm(graphView),
    SortingEventListener, LiveDataListener {


    var hasSwapAction: Boolean = false
    private val animator: BubbleAnimator = BubbleAnimator(this)




    override fun sort() {
        val indexOfLastSortedElement = barsList.size - iCounter
        if (animationState != ON_RESUME) return

        if (iCounter < barsList.size) {

            if (jCounter < indexOfLastSortedElement) {

                hasSwapAction = barsList[jCounter - 1] > barsList[jCounter]
                animator.compareStartAnimation(jCounter - 1, jCounter)


            } else {
                barsList[indexOfLastSortedElement - 1].paint.color = comparedColor
                invalidate()
                iCounter++
                jCounter = 1
                sort()
            }

        } else {
            onFinishSorting()
        }

    }


    private fun changeComparedBarsColor(color: Int) {
        barsList[jCounter - 1].paint.color = color
        barsList[jCounter].paint.color = color
        invalidate()
    }


    fun onStepFinish() {
        changeComparedBarsColor(GraphBar.barDefaultColor)

        if (hasSwapAction) {
            swap(jCounter - 1, jCounter)
        }
        jCounter++
        sort()
    }


}




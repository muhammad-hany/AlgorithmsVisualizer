package com.example.algorithmsvisualizer.algorithms

import com.example.algorithmsvisualizer.animators.InsertionAnimator
import com.example.algorithmsvisualizer.views.GraphView

class InsertionSort(graphView: GraphView) : SortAlgorithm(graphView) {

    init {
        iCounter = 0
        jCounter = iCounter + 1
    }

    override fun sort() {
        val animator: InsertionAnimator = InsertionAnimator(this)
        if (animationState!= ON_RESUME) return
        if (iCounter < barsList.size - 1) {
            if (jCounter > 1) {
                animator.moveVerticalAnimation(jCounter, InsertionAnimator.DIRECTION_DOWN)

            } else {
                onStepFinish()
            }
        }else{
            barsList[iCounter].paint.color=comparedColor
            onFinishSorting()
        }
    }

    fun onStepFinish(){
        barsList[iCounter].paint.color=comparedColor
        iCounter++
        jCounter = iCounter + 1
        sort()
    }
    override fun onStartSorting() {
        iCounter = 0
        jCounter = iCounter + 1
        animationState = ON_RESUME
        sort()
    }
}
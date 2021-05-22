package com.example.algorithmsvisualizer.animators

import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.algorithmsvisualizer.algorithms.InsertionSort

class InsertionAnimator(val sortObj: InsertionSort) {
    companion object {
        const val DIRECTION_UP = "up"
        const val DIRECTION_DOWN = "down"
        private const val moveDownYPosition = 500f
    }

    fun moveVerticalAnimation(index: Int, direction: String) {
        val defaultYPositionIndex = if (index < sortObj.barsList.size - 1) index + 1 else index - 1
        val defaultYPosition = sortObj.barsList[defaultYPositionIndex].top


        val bar = sortObj.barsList[index]
        var start = 0f
        var end = 0f
        if (direction == DIRECTION_DOWN) {
            start = defaultYPosition
            end = moveDownYPosition
        } else if (direction == DIRECTION_UP) {
            start = moveDownYPosition
            end = defaultYPosition
        }

        ValueAnimator.ofFloat(start, end).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 200
            addUpdateListener {

                bar.moveVertical(it.animatedValue as Float)
                sortObj.invalidate()


                val isAnimationStartFinished =
                    bar.top == moveDownYPosition && direction == DIRECTION_DOWN
                val isAnimationEndFinished =
                    bar.top == defaultYPosition && direction == DIRECTION_UP


                if (isAnimationStartFinished) {
                    bar.paint.color = sortObj.compareColor
                    if (bar < sortObj.barsList[index - 1]) {
                        swapLeftAnimation(index)
                    } else {
                        moveVerticalAnimation(index, DIRECTION_UP)
                    }
                } else if (isAnimationEndFinished) {
                    //step finished
                    bar.paint.color = sortObj.comparedColor
                    sortObj.onStepFinish()


                }
            }
        }.start()
    }

    private fun swapLeftAnimation(index: Int) {
        if (index == 0) {
            moveVerticalAnimation(index, DIRECTION_UP)
        } else {
            val bar1 = sortObj.barsList[index]
            val bar2 = sortObj.barsList[index - 1]
            val startPosition = bar1.center
            val endPosition = bar2.center
            val compensationFactor = bar1.center + bar2.center

            if (bar1 < bar2) {
                //make the swap
                ValueAnimator.ofFloat(startPosition, endPosition).apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 200
                    addUpdateListener {
                        bar1.moveHorizontalAbs(it.animatedValue as Float)
                        bar2.moveHorizontalAbs(compensationFactor - it.animatedValue as Float)
                        sortObj.invalidate()
                        val isSwapFinished = bar1.center == endPosition
                        if (isSwapFinished) {
                            sortObj.swap(index, index - 1)
                            swapLeftAnimation(index - 1)

                        }

                    }
                }.start()
            } else {
                //move up
                moveVerticalAnimation(index, DIRECTION_UP)
            }
        }

    }


}
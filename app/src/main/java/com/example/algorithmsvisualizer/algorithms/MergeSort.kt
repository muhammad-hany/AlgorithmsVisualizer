package com.example.algorithmsvisualizer.algorithms

import android.graphics.Color
import com.example.algorithmsvisualizer.animators.MergeAnimator
import com.example.algorithmsvisualizer.views.GraphBar
import com.example.algorithmsvisualizer.views.GraphView
import kotlin.random.Random

class MergeSort(graphView: GraphView) : SortAlgorithm(graphView) {


    private val orderedList: MutableList<MutableList<GraphBar>> = MutableList(barsList.size) {
        mutableListOf<GraphBar>(barsList[it])
    }


    private val animator: MergeAnimator = MergeAnimator(this)
    private val smallOrderedList: MutableList<GraphBar> = mutableListOf()
    private var leftList: List<GraphBar> = listOf()
    private var rightList: List<GraphBar> = listOf()
    private var leftCounter = 0
    private var rightCounter = 0



    private fun setBarsDifferentColors() {

        orderedList.forEachIndexed { index, mutableList ->
            val random = Random(index)
            val color = Color.argb(
                255,
                random.nextInt(0, 255),
                random.nextInt(0, 255),
                random.nextInt(0, 255)
            )
            mutableList[0].paint.color = color
        }
    }

    override fun sort() {
        if (animationState != ON_RESUME) return

        if (iCounter < orderedList.size - 1) {
            smallOrderedList.clear()
            leftList = orderedList[iCounter]
            rightList = orderedList[iCounter + 1]

            mergeSwap()


        } else {
            if (orderedList.size == 1) {
                //the list the fully ordered
                onFinishSorting()
            } else {
                iCounter = 0
                jCounter = 0
                sort()
            }
        }
    }


    override fun onStartSorting() {
        iCounter = 0
        jCounter = 0
        animationState = ON_RESUME
        setBarsDifferentColors()
        sort()

    }


    fun mergeSwap() {


        val bothListsEmpty = leftCounter >= leftList.size && rightCounter >= rightList.size

        if (bothListsEmpty) {
            onStepFinish()
            return
        }


        val leftListIsNotEmpty = leftCounter < leftList.size
        val rightListIsNotEmpty = rightCounter < rightList.size


        if (leftListIsNotEmpty) {
            if (rightListIsNotEmpty) {

                if (leftList[leftCounter] < rightList[rightCounter]) {
                    addOrderedBar(leftList[leftCounter], true)
                } else {
                    addOrderedBar(rightList[rightCounter], false)
                }
            } else {
                addOrderedBar(leftList[leftCounter], true)
            }
        } else {
            addOrderedBar(rightList[rightCounter], false)
        }


    }

    private fun onStepFinish() {
        val random = Random(jCounter)
        val color = Color.argb(255, random.nextInt(), random.nextInt(), random.nextInt())
        smallOrderedList.forEach {
            it.paint.color = color
        }
        animator.moveSortedBarsUp(smallOrderedList, jCounter)
        jCounter += smallOrderedList.size
        leftCounter = 0
        rightCounter = 0
        orderedList[iCounter] = smallOrderedList.toMutableList()
        orderedList.removeAt(iCounter + 1)
        iCounter++

    }

    private fun addOrderedBar(element: GraphBar, isItLeftList: Boolean) {
        smallOrderedList.add(element)
        animator.moveBarToComparePosition(
            element,
            smallOrderedList.indexOf(element)
        )
        if (isItLeftList) leftCounter++ else rightCounter++

    }


}
package com.example.algorithmsvisualizer.algorithms

import android.graphics.Color
import android.util.Log
import com.example.algorithmsvisualizer.animators.QuickAnimator
import com.example.algorithmsvisualizer.views.GraphBar
import com.example.algorithmsvisualizer.views.GraphView

class QuickSort(graphView: GraphView) : SortAlgorithm(graphView) {

    var sortList: MutableList<GraphBar> = mutableListOf()

    private var orderedList: MutableList<MutableList<GraphBar>> =
        mutableListOf(barsList.toMutableList())
    private var pivot = orderedList[0][0]
    private val animator = QuickAnimator(this)

    override fun sort() {
        if (animationState!= ON_RESUME) return
        val pivotInCorrectPosition = iCounter == jCounter

        if (pivotInCorrectPosition) {
            onStepFinish()
        } else {
            quickSort()
        }

    }

    private fun onStepFinish() {
        pivot.paint.color= comparedColor
        val index = orderedList.indexOf(sortList)
        orderedList.removeAt(index)
        orderedList.addAll(index, sortList.split(iCounter))
        sortList.clear()
        for (item in orderedList) {
            if (item.size > 1) {
                sortList = item
                pivot = sortList[0]
                break
            } else {
                sortList = mutableListOf()
                if (item.isNotEmpty()) item[0].paint.color=comparedColor
            }
        }

        iCounter = 0
        jCounter = sortList.size - 1
        if (sortList.isNotEmpty()) sort() else onFinishSorting()
    }


    private fun quickSort() {
        val pivotInLeftSide = sortList.indexOf(pivot) == iCounter
        val compareBarIndex = if (pivotInLeftSide) iCounter else jCounter


        animator.compareStartAnimation(sortList.indexOf(pivot), compareBarIndex, pivotInLeftSide)


    }


    fun comparePivotWithJCounter() {
        if (pivot > sortList[jCounter]) {
            animator.compareSwapAnimation(sortList.indexOf(pivot), jCounter)
            iCounter++

        } else {
            animator.compareEndAnimation(sortList.indexOf(pivot),jCounter)
            jCounter--

        }
    }

    fun comparePivotWithICounter() {
        if (pivot > sortList[iCounter]) {
            animator.compareEndAnimation(sortList.indexOf(pivot),iCounter)
            iCounter++

        } else {
            animator.compareSwapAnimation(sortList.indexOf(pivot), iCounter)
            jCounter--

        }
    }


    override fun swap(x1: Int, x2: Int) {

        var temp = sortList[x1]
        sortList[x1] = sortList[x2]
        sortList[x2] = temp

        val index1 = barsList.indexOf(sortList[x1])
        val index2 = barsList.indexOf(sortList[x2])

        temp = barsList[index1]
        barsList[index1] = barsList[index2]
        barsList[index2] = temp

    }

    override fun onStartSorting() {

        iCounter = 0
        jCounter = barsList.size - 1
        animationState = ON_RESUME
        sortList = orderedList.first { it.size > 1 }
        pivot = sortList[0]
       // pivot.paint.color=compareColor
        Log.i("LIST", "before sorting .....  $barsList")
        sort()
    }

    override fun onFinishSorting() {
        super.onFinishSorting()
        Log.i("LIST", "after sorting ..... $barsList")
    }

    private fun MutableList<GraphBar>.split(pivot: Int): MutableList<MutableList<GraphBar>> {
        val list = mutableListOf<MutableList<GraphBar>>()
        if (this.size == 2) {
            list.add(mutableListOf(this[0]))
            list.add(mutableListOf(this[1]))
        } else if (this.size > 2) {

            list.add(this.subList(0, pivot).toMutableList())
            list[0].forEach { it.paint.color=Color.parseColor("#2a9d8f") }
            list.add(mutableListOf(this[pivot]))
            list.add(this.subList(pivot + 1, this.size).toMutableList())
            list[2].forEach { it.paint.color=Color.parseColor("#e76f51") }
        }

        return list

    }
}
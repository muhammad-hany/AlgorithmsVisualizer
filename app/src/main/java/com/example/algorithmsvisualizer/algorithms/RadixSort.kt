package com.example.algorithmsvisualizer.algorithms

import com.example.algorithmsvisualizer.SortingEventListener
import com.example.algorithmsvisualizer.animators.RadixAnimator
import com.example.algorithmsvisualizer.views.Radix
import com.example.algorithmsvisualizer.views.RadixView

class RadixSort(val radixView: RadixView) : SortingEventListener {


    companion object {
        const val ON_PAUSE: String = "pause"
        const val ON_RESUME: String = "resume"
    }

    var iCounter = 0
    var jCounter = 1
    var kCounter=0

    val sortedList: MutableList<MutableList<Radix>> = MutableList(10){
        mutableListOf()
    }

    var animationState = ON_PAUSE
    val animator = RadixAnimator(this)


    fun sort() {
        if (animationState != ON_RESUME) return
        if (iCounter < radixView.sortList.size) {
            sortNextStep()
        } else {
            rearrangeElements()
        }
    }

    private fun rearrangeElements() {
        radixView.sortList.clear()
        sortedList.forEach {
            if (it.isNotEmpty()) {
                radixView.sortList.addAll(it.toMutableList())
                it.clear()
            }

        }
        animator.baseIndicesSize = MutableList(10) { 1 }
        moveSortedElementsUp()
    }

    private fun moveSortedElementsUp() {

        if (kCounter < radixView.sortList.size) {
            animator.translateToGrid(kCounter,radixView.sortList[kCounter])

        }else{
            onStepFinish()
        }

    }
    fun moveUpNextElement(){
        radixView.sortList[kCounter].compareIndex=jCounter-1
        kCounter++
        moveSortedElementsUp()
    }

    private fun sortNextStep() {

        val radix = radixView.sortList[iCounter]
        val baseIndex = radix.text.toInt().getDigitByIndex(jCounter)
        animator.translateToBase(baseIndex, radix)
    }


    fun sortNextItem() {
        iCounter++
        sort()
    }

    fun onStepFinish() {
        jCounter--
        if (jCounter < 0) {
            onFinishSorting()
        }else {
            iCounter = 0
            kCounter = 0
            sort()
        }
    }


    fun Int.getDigitByIndex(index: Int): Int {
        return this.toString().substring(index, index + 1).toInt()
    }


    override fun onStartSorting() {
        iCounter = 0
        jCounter = radixView.sortList[0].text.length - 1
        animationState = SortAlgorithm.ON_RESUME
        sort()
    }

    override fun onResumeSorting() {
        animationState = SortAlgorithm.ON_RESUME
        sort()
    }

    override fun onPauseSorting() {
        animationState = SortAlgorithm.ON_PAUSE
    }

    override fun onFinishSorting() {
        radixView.onFinishSorting()
    }

    fun invalidate() {
        radixView.invalidate()
    }


}
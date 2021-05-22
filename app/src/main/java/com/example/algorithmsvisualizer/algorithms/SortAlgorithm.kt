package com.example.algorithmsvisualizer.algorithms

import android.graphics.Color
import android.util.Log
import com.example.algorithmsvisualizer.LiveDataListener
import com.example.algorithmsvisualizer.SortingEventListener
import com.example.algorithmsvisualizer.views.GraphBar
import com.example.algorithmsvisualizer.views.GraphView

open class SortAlgorithm(val graphView: GraphView) : SortingEventListener,LiveDataListener {

    companion object {
        const val ON_PAUSE: String = "pause"
        const val ON_RESUME: String = "resume"
    }

    var iCounter = 0
    var jCounter = 1

    var animationState = ON_PAUSE
    var barsList: ArrayList<GraphBar> = graphView.barsList


    val compareColor: Int = Color.argb(255, 211, 239, 0)
    val comparedColor: Int = Color.argb(255, 4, 204, 20)

    open fun sort() {
        Log.i("DLG", "log from parent class")
    }


    override fun onStartSorting() {
        iCounter = 0
        jCounter = 1
        animationState = ON_RESUME
        sort()
    }

    override fun onResumeSorting() {
        animationState = ON_RESUME
        sort()
    }

    override fun onPauseSorting() {
        animationState = ON_PAUSE
    }

    override fun onFinishSorting() {
        graphView.onFinishSorting()
    }

    override fun onDataChange(progress: Int) {
        barsList = graphView.barsList
    }

    open fun swap(x1: Int, x2: Int) {

        val temp = barsList[x2]
        barsList[x2] = barsList[x1]
        barsList[x1] = temp
    }

    fun invalidate() = graphView.invalidate()

}
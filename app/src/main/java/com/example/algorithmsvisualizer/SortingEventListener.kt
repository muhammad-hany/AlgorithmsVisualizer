package com.example.algorithmsvisualizer

import android.animation.ValueAnimator

interface SortingEventListener {
    fun onStartSorting()
    fun onResumeSorting()
    fun onPauseSorting()
    fun onFinishSorting()
}

interface LiveDataListener{
    fun onDataChange(progress: Int)
}
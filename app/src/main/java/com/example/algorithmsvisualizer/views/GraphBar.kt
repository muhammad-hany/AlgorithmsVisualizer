package com.example.algorithmsvisualizer.views

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

open class GraphBar(var center: Float, top: Float, val barThickness: Float, val barHeight: Float) :
    RectF(center - (barThickness / 2), top, center + (barThickness / 2), top + barHeight) {

    companion object{
         val barDefaultColor:Int=Color.CYAN
    }


    var newPosition: Float = center
        set(value) {
            center = value
            left = center - (barThickness / 2)
            right = center + (barThickness / 2)

        }

    var paint:Paint=Paint().apply { color= barDefaultColor }
        set(value) {
            lastPaint=paint
            field = value
        }

    var lastPaint=paint


    fun moveHorizontalAbs(xPosition: Float) {
        center = xPosition
        left = center - (barThickness / 2)
        right = center + (barThickness / 2)
    }

    fun moveHorizontalAvg(xPosition: Float){
        moveHorizontalAbs(center+xPosition)
    }

    fun moveVertical(yPosition: Float){
        top=yPosition
        bottom=top+barHeight
    }




    operator fun compareTo(bar: GraphBar): Int {
        return barHeight.toInt() - bar.barHeight.toInt()
    }

    override fun toString(): String {
        return barHeight.toString()
    }

}
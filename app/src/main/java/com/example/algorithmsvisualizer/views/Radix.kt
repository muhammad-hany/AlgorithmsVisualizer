package com.example.algorithmsvisualizer.views

import android.graphics.*
import kotlin.math.ceil

class Radix(val text: String, val index: Int, val viewWidth: Float, val viewHeight: Float) {

    var paint: Paint = Paint()
    var compareIndex = text.length - 1
    var outerBox: Rect = Rect()
    val numberOfRows = 5
    val numberOfColumns = 5
    val padding=viewWidth/108


    var fontSize = viewWidth/27
        set(value) {
            paint.textSize = value
            field = value
        }

    var left = 0f
    var bottom = 0f

    init {


        paint.textSize = fontSize
        paint.getTextBounds(text, 0, text.length, outerBox)
        outerBox.applyPadding(padding.toInt())
        left = getBoxLeftFromIndex(index)
        bottom = getBoxBottomFromIndex(index)
        outerBox.translateAbs(left, bottom)



    }


    fun drawRadix(canvas: Canvas?) {

        val textLeft=left+padding
        val textBottom=bottom-padding

        text.forEachIndexed { index, c ->
            paint.color = if (index == compareIndex) Color.RED else Color.BLACK
            val x = if (index < 1) textLeft else textLeft + getXFromLetterIndex(index)
            canvas?.drawText(c.toString(), x, textBottom, paint)

        }



        canvas?.drawRect(outerBox, Paint().apply {
            strokeWidth = 2f
            style = Paint.Style.STROKE
        })


    }

    private fun getXFromLetterIndex(index: Int): Float {
        return paint.measureText(text, 0, index)
    }

     fun getBoxLeftFromIndex(index: Int): Float {
        val boxWidth = outerBox.width()
        val leftMargin = (viewWidth - numberOfColumns * boxWidth) / (numberOfColumns+1)
        val g: Float = ((index + 1).toFloat()) / numberOfColumns
        val fraction: Float = g - g.toInt()
        val columnIndex = if (fraction == 0f) numberOfColumns.toFloat() else fraction * numberOfColumns

        return columnIndex * leftMargin + (columnIndex - 1) * boxWidth
    }

    fun getBoxBottomFromIndex(index: Int): Float {
        val boxHeight = outerBox.height()
        val topMargin = ((viewWidth / 2 - numberOfRows * boxHeight) / (numberOfRows+1)) + boxHeight

        val rawIndex: Float = ceil((index+1) / numberOfRows.toFloat())

        return rawIndex * topMargin + (rawIndex - 1) * boxHeight

    }

    private fun Rect.translateRelative(x: Float, y: Float) {
        this.left += x.toInt()
        this.right += x.toInt()
        this.top += y.toInt()
        this.bottom += y.toInt()
    }
    private fun Rect.translateAbs(x: Float, y: Float) {
        val width=this.width()
        val height=this.height()
        this.left = x.toInt()
        this.right = left+width
        this.top = y.toInt()-height
        this.bottom = y.toInt()


    }

     fun Rect.applyPadding(padding: Int) {
        this.left -= padding
        this.top -= padding
        this.right += padding
        this.bottom += padding
    }

    override fun toString(): String {
        return text
    }

    fun move(x:Float,y:Float){
        left=x
        bottom=y
        outerBox.translateAbs(left,bottom)

    }

}
package com.example.algorithmsvisualizer.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.algorithmsvisualizer.LiveDataListener
import com.example.algorithmsvisualizer.MainActivity
import com.example.algorithmsvisualizer.SortingEventListener
import com.example.algorithmsvisualizer.algorithms.RadixSort
import kotlin.random.Random

class RadixView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), SortingEventListener {

    private var radixNumber: Int = 25
    private var viewWidth = 0
    private var viewHeight = 0

    var sortList = mutableListOf<Radix>()
    var baseRects = mutableListOf<Rect>()
    lateinit var radixSort: RadixSort


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawRadix(canvas)
        drawBase(canvas)
    }

    private fun drawBase(canvas: Canvas?) {
        val paint = Paint().apply {
            textSize = 50f
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        val box = Rect()
        sortList[0].paint.getTextBounds(sortList[0].text, 0, sortList[0].text.length, box)
        box.applyPadding(sortList[0].padding.toInt())


        val leftMargin = (width - (box.width() * 10)) / 11
        var left = leftMargin
        val bottom = height - 20f
        for (i in 0..9) {
            val rect = Rect(
                left.toInt(),
                bottom.toInt() - box.height(),
                left.toInt() + box.width(),
                bottom.toInt()
            )
            canvas?.drawRect(rect, paint)
            val wordWidth = paint.measureText(i.toString())
            canvas?.drawText(
                i.toString(),
                rect.centerX() - (wordWidth / 2),
                rect.bottom - 5f,
                paint
            )
            baseRects.add(rect)
            left += box.width() + leftMargin

        }
    }

    private fun drawRadix(canvas: Canvas?) {

        sortList.forEach { it.drawRadix(canvas) }

        canvas?.drawLine(0f, height / 2f, width.toFloat(), height / 2f, Paint())

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        viewWidth = w
        sortList = MutableList(radixNumber) {
            Radix(Random(it).nextInt(100, 999).toString(), it, width.toFloat(), h.toFloat())
        }
        radixSort = RadixSort(this)
    }

    override fun onStartSorting() {
        radixSort.onStartSorting()
    }

    override fun onResumeSorting() {
        radixSort.onResumeSorting()
    }

    override fun onPauseSorting() {
        radixSort.onPauseSorting()
    }

    override fun onFinishSorting() {
        (context as MainActivity).onFinishSorting()
    }


    fun Rect.applyPadding(padding: Int) {
        this.left -= padding
        this.top -= padding
        this.right += padding
        this.bottom += padding
    }


}
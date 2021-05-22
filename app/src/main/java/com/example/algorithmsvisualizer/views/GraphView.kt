package com.example.algorithmsvisualizer.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.algorithmsvisualizer.LiveDataListener
import com.example.algorithmsvisualizer.MainActivity
import com.example.algorithmsvisualizer.R
import com.example.algorithmsvisualizer.SortingEventListener
import com.example.algorithmsvisualizer.algorithms.*
import kotlin.random.Random


class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), SortingEventListener, LiveDataListener {


    var barsDefaultTop: Float = 0f
    var barsSpacing: Float = 0f
    private var barWidth = 0f
    private var totalBarsWidth = 0f
    private var barsNumber: Int = 5
    var barsList: ArrayList<GraphBar> = ArrayList(barsNumber)
    private var screenWidth: Float = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawGraph(canvas)

    }


    private fun drawGraph(canvas: Canvas?) {
        for (i in barsList.indices) {
            canvas?.drawRect(barsList[i], barsList[i].paint)
        }


    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i("TAGGY", "onSizeChanged called !")
        screenWidth = w.toFloat()
        setUpGraphBars()
    }


    private fun setUpGraphBars() {
        barsDefaultTop = screenWidth / 54
        val canvasWidthAfterPadding: Float = screenWidth - barsDefaultTop * 2   //1040
        barWidth = canvasWidthAfterPadding / (2 * barsNumber)  //104
        barsSpacing = (canvasWidthAfterPadding - (barWidth * barsNumber)) / barsNumber //104
        totalBarsWidth = (barsNumber * barWidth) + ((barsNumber - 1) * barsSpacing)






        for (i in 0 until barsNumber) {
            val xCenter = getBarCenterPositionFromIndex(i)
            val barHeight = Random(i).nextInt(1000).toFloat()
            barsList.add(GraphBar(xCenter, barsDefaultTop, barWidth, barHeight))

        }


    }

    fun getBarCenterPositionFromIndex(index: Int) =
        ((screenWidth - totalBarsWidth) / 2 + barWidth / 2) + index * (barsSpacing + barWidth)


    private var sortAlgorithm: SortAlgorithm? = null

    fun sortElements(algorithmType: String) {
        when (algorithmType) {
            resources.getString(R.string.bubble_sort) -> {
                sortAlgorithm = BubbleSort(this)
                (sortAlgorithm as BubbleSort).onStartSorting()
            }
            resources.getString(R.string.insertion_sort) -> {
                sortAlgorithm = InsertionSort(this)
                (sortAlgorithm as InsertionSort).onStartSorting()
            }
            resources.getString(R.string.selections_sort) -> {
                sortAlgorithm = SelectionSort(this)
                (sortAlgorithm as SelectionSort).onStartSorting()
            }
            resources.getString(R.string.merge_sort) -> {
                sortAlgorithm = MergeSort(this)
                (sortAlgorithm as MergeSort).onStartSorting()
            }
            resources.getString(R.string.quick_sort) ->{
                sortAlgorithm=QuickSort(this)
                (sortAlgorithm as QuickSort).onStartSorting()
            }
        }
    }


    private fun setUpNewBars(newBarsNumber: Int) {
        barsList.clear()
        barsNumber = newBarsNumber
        setUpGraphBars()
        invalidate()

    }


    override fun onStartSorting() {
        sortAlgorithm?.onStartSorting()
    }

    override fun onResumeSorting() {
        sortAlgorithm?.onResumeSorting()
    }

    override fun onPauseSorting() {
        sortAlgorithm?.onPauseSorting()
    }

    override fun onFinishSorting() {
        (context as MainActivity).onFinishSorting()
    }

    override fun onDataChange(progress: Int) {
        setUpNewBars(progress)
        sortAlgorithm?.onDataChange(progress)
    }


}



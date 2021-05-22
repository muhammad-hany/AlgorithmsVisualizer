package com.example.algorithmsvisualizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.algorithmsvisualizer.algorithms.SelectionSort
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener , SortingEventListener  {

    private lateinit var algorithmTypes: Array<String>
    private lateinit var algorithmType: String
    private var isAnimationIsActive=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        defineViews()
        algorithmTypes = resources.getStringArray(R.array.algorithms);
        algorithmType = algorithmTypes[0]
    }


    private fun defineViews() {
        seekBar.min = 5
        seekBar.max = 50
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                graphView.onDataChange(progress)
                Log.i("TAGG", "bars number is $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })



        spinner.onItemSelectedListener = this

        startButton.setOnClickListener {
            graphView.sortElements(algorithmType)
            onStartSorting()


        }

        playPauseButton.setOnClickListener {
            if (isAnimationIsActive) {
                onPauseSorting()
            }else{
                onResumeSorting()
            }
        }





    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        algorithmType = algorithmTypes[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onStartSorting() {
        isAnimationIsActive=true
        setViewActiveState(false)
    }

    override fun onResumeSorting() {
        isAnimationIsActive=true
        setViewActiveState(false)
        graphView.onResumeSorting()
    }

    override fun onPauseSorting() {
        isAnimationIsActive=false
        setViewActiveState(true)
        graphView.onPauseSorting()
    }

    override fun onFinishSorting() {
        setViewActiveState(true)
    }

    private fun setViewActiveState(isItEnabled: Boolean){
        seekBar.isEnabled=isItEnabled
        startButton.isEnabled=isItEnabled
        spinner.isEnabled=isItEnabled
    }




}

package com.example.algorithmsvisualizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.algorithmsvisualizer.algorithms.SelectionSort
import com.example.algorithmsvisualizer.views.GraphView
import com.example.algorithmsvisualizer.views.RadixView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener , SortingEventListener  {

    private lateinit var algorithmTypes: Array<String>
    private lateinit var algorithmType: String
    private var isAnimationIsActive=false
    lateinit var customView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        defineViews()
        algorithmTypes = resources.getStringArray(R.array.algorithms);
        algorithmType = algorithmTypes[0]
    }


    private fun defineViews() {
        customView=LayoutInflater.from(this).inflate(R.layout.graph_layout,null)
        frameLayout.addView(customView)

        seekBar.min = 5
        seekBar.max = 50
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                (customView as GraphView).onDataChange(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })



        spinner.onItemSelectedListener = this

        startButton.setOnClickListener {

            if (spinner.selectedItemPosition==algorithmTypes.indexOf(resources.getString(R.string.radix_sort))){

                (customView as RadixView).onStartSorting()
            }else{

                (customView as GraphView).sortElements(algorithmType)

            }

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
        var layoutId=-1
        when(position){
            algorithmTypes.indexOf(resources.getString(R.string.radix_sort))->{
                layoutId=R.layout.radix_layout
                seekBar.isEnabled=false
                algorithmType = algorithmTypes[position]
                customView=LayoutInflater.from(this).inflate(layoutId,null)
                frameLayout.removeAllViews()
                frameLayout.addView(customView)
            }
            else->{
                layoutId=R.layout.graph_layout
                if (algorithmType==resources.getString(R.string.radix_sort)){
                    customView=LayoutInflater.from(this).inflate(layoutId,null)
                    frameLayout.removeAllViews()
                    frameLayout.addView(customView)
                }
                seekBar.isEnabled=true
                algorithmType = algorithmTypes[position]
            }

        }



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
        if (algorithmType!=resources.getString(R.string.radix_sort)) {
            (customView as GraphView).onResumeSorting()
        }else{
            (customView as RadixView).onResumeSorting()
        }
    }

    override fun onPauseSorting() {
        isAnimationIsActive=false
        setViewActiveState(true)
        if (algorithmType!=resources.getString(R.string.radix_sort)) {
            (customView as GraphView).onPauseSorting()
        }else{
            (customView as RadixView).onPauseSorting()
        }
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

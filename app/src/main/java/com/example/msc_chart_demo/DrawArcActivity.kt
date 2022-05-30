package com.example.msc_chart_demo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class DrawArcActivity : AppCompatActivity() {

    lateinit var arcView : ArcView
    lateinit var seekbar : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_draw)

        val listener = object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.d("seekbar", "p1 : $p1, p2 : $p2")
                var weight: Float = 0f
                if (p1 == 0) {
                    weight = 0.01f
                } else {
                    weight = p1.toFloat()

                    if (weight == 4f)
                        weight = 4.3f
                }

                arcView.setFillMiddleWeight(weight)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        }

        try {
            arcView = findViewById<ArcView>(R.id.arcView)
            seekbar = findViewById(R.id.seekbar)


            seekbar.setOnSeekBarChangeListener(listener)
//            arcView.setFillMiddleWeight(2f)


        } catch (e:Exception) {

        }





    }

}
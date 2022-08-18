package com.example.msc_chart_demo

import android.graphics.Point
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import java.lang.Exception

class DrawArc5Activity : AppCompatActivity() {
    lateinit var arcView: ArcView5
    lateinit var seekbar : SeekBar
    lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_arc5)

        val listener = object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.d("ArcView5", "Seekbar : p1 : $p1 , p2 : $p2")
                var weight: Float = 0f
                if (p1 == 0) {
                    weight = 0.01f
                } else {
                    weight = (p1.toFloat() * 0.1).toFloat() + 0.025f
                }

                arcView.setFillMiddleWeight(weight)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        }

        val displaySize = Point()
        windowManager.defaultDisplay.getRealSize(displaySize)

        val windowSize = Rect()
        window.decorView.getWindowVisibleDisplayFrame(windowSize)

        Log.e("WindowScreen", "display.width: ${displaySize.x}, display.height: ${displaySize.y}")

//        val standardSize: Float = (displaySize.y * 0.65).toFloat()
        try {
            arcView = findViewById<ArcView5>(R.id.arcView).apply {
                screenHeight = (displaySize.y).toFloat()
                screenWidth = (displaySize.x).toFloat()
                invalidate()
            }

        } catch (e: Exception) {

        }

        seekbar = findViewById(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(listener)

        button = findViewById(R.id.btn_title)
        button.setOnClickListener {
            arcView.useCenterLine = !arcView.useCenterLine
            arcView.invalidate()
        }
    }
}
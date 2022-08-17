package com.example.msc_chart_demo

import android.graphics.Point
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.Exception

class DrawArc4Activity : AppCompatActivity() {

    lateinit var arcView: ArcView4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_arc4)

        val displaySize = Point()
        windowManager.defaultDisplay.getRealSize(displaySize)

        val windowSize = Rect()
        window.decorView.getWindowVisibleDisplayFrame(windowSize)

        Log.e("WindowScreen", "display.width: ${displaySize.x}, display.height: ${displaySize.y}")

//        val standardSize: Float = (displaySize.y * 0.65).toFloat()
        try {
            arcView = findViewById<ArcView4>(R.id.arcView).apply {
                screenHeight = (displaySize.y).toFloat()
                screenWidth = (displaySize.x).toFloat()
                invalidate()
            }

        } catch (e: Exception) {

        }
    }
}
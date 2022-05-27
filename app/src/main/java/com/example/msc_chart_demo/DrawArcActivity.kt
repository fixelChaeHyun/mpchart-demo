package com.example.msc_chart_demo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DrawArcActivity : AppCompatActivity() {

    lateinit var arcView : ArcView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_draw)

        arcView = findViewById<ArcView>(R.id.arcView)


    }

}
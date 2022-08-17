package com.example.msc_chart_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class DrawArc3Activity : AppCompatActivity() {

    lateinit var arcView : ArcView3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_arc3)

        try {
            arcView = findViewById<ArcView3>(R.id.arcView)

        } catch (e: Exception) {

        }
    }
}
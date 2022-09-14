package com.example.msc_chart_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ArcLayoutActivity : AppCompatActivity() {

    lateinit var customArcLayout: CustomArcLayout

    lateinit var buttonTitle: Button
    lateinit var buttonShot: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arc_layout)

        customArcLayout = findViewById(R.id.customArcLayout)
        customArcLayout.requestLayout()

        buttonTitle = findViewById(R.id.btn_title)
        buttonShot = findViewById(R.id.btn_shot)

        buttonTitle.setOnClickListener {
            customArcLayout.showValidShotArea()
        }
        buttonTitle.setOnLongClickListener {
            customArcLayout.showFairwayGridLine()
            true
        }

        buttonShot.setOnClickListener {
            customArcLayout.makeShot()
        }

        buttonShot.setOnLongClickListener {
            customArcLayout.clearShotLayout()
            true
        }
    }
}
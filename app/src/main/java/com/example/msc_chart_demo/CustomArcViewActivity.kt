package com.example.msc_chart_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar

class CustomArcViewActivity : AppCompatActivity() {
    private val TAG = CustomArcViewActivity::class.java.simpleName

    lateinit var buttonTitle: Button
    lateinit var arcView: CustomArcView
    lateinit var seekbar: SeekBar

    var initialWidth = 0f
    var initialHeight = 0f

    private val seekbarListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (progress == 0) return

            val scale = 1f / progress.toFloat()
            arcView.redrawArcView(
                (initialWidth * scale).toInt(),
                (initialHeight * scale).toInt(),
                scale
            )
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_arc_view)

        initialWidth = Math.min(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels) * 0.9f
        initialHeight = Math.min(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels) * 0.9f

        arcView = findViewById(R.id.customArcView)

        buttonTitle = findViewById(R.id.btn_title)
        buttonTitle.setOnClickListener {
            arcView.showValidShotArea = !arcView.showValidShotArea
            arcView.invalidate()
        }
        buttonTitle.setOnLongClickListener {
            arcView.showGridLine = !arcView.showGridLine
            arcView.invalidate()
            true
        }

        seekbar = findViewById(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(seekbarListener)
    }
}
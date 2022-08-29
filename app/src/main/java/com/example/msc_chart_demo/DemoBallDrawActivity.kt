package com.example.msc_chart_demo

import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import com.example.msc_chart_demo.data.CustomBallData

class DemoBallDrawActivity : AppCompatActivity() {
    private val TAG = DemoBallDrawActivity::class.java.simpleName

    lateinit var circleBallImageView: CircleBallImageView
    lateinit var buttonTitle: Button
    lateinit var buttonShot: Button
    lateinit var seekBar: SeekBar
    lateinit var seekBar2: SeekBar

    var initialWidth = 0f
    var initialHeight = 0f

    private val seekbarListener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            Log.v(TAG, "seekbarListener -> progress: $progress")
            if (progress == 0) return

            if (initialWidth == 0f || initialHeight == 0f) {
                return
            }

            val viewScale = 1f / progress.toFloat()
            circleBallImageView.viewScale = viewScale
            circleBallImageView.layoutParams.width = (initialWidth * viewScale).toInt()
            circleBallImageView.layoutParams.height = (initialHeight * viewScale).toInt()
            circleBallImageView.requestLayout()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    private val seekbarListener2 = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (progress == 0) return
            // 1~5
            val zoomValue = listOf(0.3f, 0.5f, 1f, 1.5f, 1.8f)

            if (initialWidth == 0f || initialHeight == 0f) {
                return
            }

            var zoomScale = zoomValue[progress]
            circleBallImageView.zoomScale = zoomScale
            circleBallImageView.requestLayout()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_ball_draw)

        circleBallImageView = findViewById(R.id.circleBallImageView)

        val displaySize = Point()
        windowManager.defaultDisplay.getRealSize(displaySize)

        Log.e(TAG, " ScreenSize >> screen.width: ${displaySize.x} , screen.height: ${displaySize.y}")

        val widthPixels = resources.displayMetrics.widthPixels
        val heightPixels = resources.displayMetrics.heightPixels
        Log.e(TAG, " ScreenPixels >> pixelWidth: $widthPixels , pixelHeight: $heightPixels")
        Log.i(TAG, " ballView.width: ${circleBallImageView.width}, ballView.height: ${circleBallImageView.height}")


        initialWidth = Math.min(displaySize.x, displaySize.y) * 0.8f
        initialHeight = Math.min(displaySize.x, displaySize.y) * 0.8f

        buttonTitle = findViewById(R.id.btn_title)
        buttonTitle.setOnClickListener {
            circleBallImageView.toggleGridLine()
        }

        seekBar = findViewById(R.id.seekbar)
        seekBar.setOnSeekBarChangeListener(seekbarListener)

        seekBar2 = findViewById(R.id.seekbar2)
        seekBar2.setOnSeekBarChangeListener(seekbarListener2)

        val dummies = listOf(
            CustomBallData("I9", Color.argb(255,187, 34, 252), true),
            CustomBallData("I5", Color.argb(255, 98, 0, 238), false),
            CustomBallData("W3", Color.argb(255, 37, 84, 253), false),
            CustomBallData("W5", Color.argb(255, 0, 37, 170), true),
            CustomBallData("1"),
            CustomBallData("12", showOutline = true),
            CustomBallData("25"),
            CustomBallData("37", showOutline = true),
            CustomBallData("56"),
            CustomBallData("96", showOutline = true),
            CustomBallData("100")
        )
        var dummyIndex = 0
        buttonShot = findViewById(R.id.btn_shot)
        buttonShot.setOnClickListener {
            dummyIndex += 1
            dummyIndex %= dummies.size
            circleBallImageView.ballData = dummies[dummyIndex]
            circleBallImageView.invalidate()
        }

    }
}
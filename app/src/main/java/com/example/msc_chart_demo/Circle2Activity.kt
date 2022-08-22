package com.example.msc_chart_demo

import android.graphics.Point
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.lang.Exception
import java.util.*

class Circle2Activity : AppCompatActivity() {
    lateinit var arcView: ArcView7
    lateinit var seekbar : SeekBar
    lateinit var button : Button
    lateinit var buttonShot : Button
    lateinit var textDistance : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle2)

        val listener = object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.d("CircleActivity", "Seekbar : p1 : $p1 , p2 : $p2")

                val zoom = listOf(0.1f, 0.5f, 0.75f, 1.0f)
                arcView.setFillMiddleWeight(zoom[p1])
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
//        val standardSize: Float = (displaySize.y * 0.65).toFloat()
        try {
            arcView = findViewById<ArcView7>(R.id.arcView).apply {
                screenHeight = (displaySize.y).toFloat()
                screenWidth = (displaySize.x).toFloat()

                invalidate()
            }

        } catch (e: Exception) {
            e.printStackTrace()

        }

        seekbar = findViewById(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(listener)

        button = findViewById(R.id.btn_title)
        button.setOnClickListener {
            arcView.validAreaForShotData = !arcView.validAreaForShotData
            arcView.invalidate()
        }
        button.setOnLongClickListener {
            Log.w("CircleActivity", "LongClick-()")
            arcView.setLongClick = !arcView.setLongClick
            arcView.invalidate()
            true
        }


        val dummies = listOf<BallShotData>(
            BallShotData(250f, 10.0, "W", Const.ballColors[3]),
            BallShotData(250f, -15.0, "U3", Const.ballColors[0]),
            BallShotData(123f, -3.0, "I6", Const.ballColors[1]),
            BallShotData(120f, 11.0, "U3", Const.ballColors[0]),
            BallShotData(68f, -7.8, "U3", Const.ballColors[0]),
            BallShotData(143f, 0.0, "W", Const.ballColors[3]),
            BallShotData(217f, 6.5, "I6", Const.ballColors[1]),
            BallShotData(103f, 5.0, "I3", Const.ballColors[2]),
            BallShotData(123f, -5.0, "W", Const.ballColors[3]),
            BallShotData(263f, 0.5, "I6", Const.ballColors[1]),
            BallShotData(260f, -19.5, "I6", Const.ballColors[1]),
            BallShotData(275f, 19.5, "I3", Const.ballColors[2]),
            BallShotData(233f, 0.5, "I3", Const.ballColors[2]),
            BallShotData(248f, -0.5, "I9", Const.ballColors[4]),
            BallShotData(50f, 7.5, "I9", Const.ballColors[4]),
            BallShotData(178f, -10.5, "U3", Const.ballColors[0]),
            BallShotData(161f, 4.5, "U3", Const.ballColors[0]),
            BallShotData(200f, -10.5, "I9", Const.ballColors[4]),
        )
        textDistance = findViewById(R.id.distanceText)

        var clickedIndex = 0
        buttonShot = findViewById(R.id.btn_shot)
        buttonShot.setOnClickListener {
            textDistance.text = "거리: ${dummies[clickedIndex].distance}, 각도: ${dummies[clickedIndex].rad}"
            arcView.setShotData(listOf(dummies[clickedIndex]))

            clickedIndex += 1
            if (clickedIndex % dummies.size == 0) {
                clickedIndex = 0
            }
            Log.e("Circle2", "clickedIndex : $clickedIndex , dummies.size: ${dummies.size}")
        }

        buttonShot.setOnLongClickListener {
            textDistance.text = "Show all dummies"
            arcView.setShotData(dummies)
            true
        }


        arcView.viewWidth = arcView.width.toFloat()
        arcView.viewHeight = arcView.height.toFloat()
        Log.e("WindowScreen", "display.width: ${displaySize.x}, display.height: ${displaySize.y}")
        Log.e("WindowScreen", "arcView.width: ${arcView.width}, arcView.height: ${arcView.height} \n-> measuredWidth: ${arcView.measuredWidth} , measuredHeight: ${arcView.measuredHeight}")


    }
}
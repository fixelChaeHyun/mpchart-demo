package com.example.msc_chart_demo

import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast

class TextTestActivity : AppCompatActivity() {
    private val TAG = TextTestActivity::class.java.simpleName

    lateinit var ballCustomView: BallCustomView
    lateinit var titleButton: Button
    lateinit var shotButton: Button
    lateinit var seekbar: SeekBar

    var initialWidth = 0f
    var initialHeight = 0f

    val listener = object: SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            Log.d(TAG, "Seekbar : p1 : $p1 , p2 : $p2")

            if (initialWidth == 0f || initialHeight == 0f) {
                return
            }
            // defined size 984 * 984
            val zoom = 1f / p1.toFloat()
            val definedWidth = initialWidth
            val definedHeight = initialHeight

            Log.d(TAG, " > zoom : $zoom")
            val scaledWidth = definedWidth * zoom
            val scaledHeight = definedHeight * zoom
            ballCustomView.scale = zoom
            ballCustomView.layoutParams.width = scaledWidth.toInt()
            ballCustomView.layoutParams.height = scaledHeight.toInt()
            ballCustomView.requestLayout()
            ballCustomView.invalidate()

        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_test)

        val displaySize = Point()
        windowManager.defaultDisplay.getRealSize(displaySize)

        titleButton = findViewById(R.id.btn_title)
        shotButton = findViewById(R.id.btn_shot)
        ballCustomView = findViewById(R.id.centerText)
        Log.d(TAG, " >> INIT : screen.width: ${displaySize.x} , screen.height: ${displaySize.y}")
        Log.d(TAG, " >> INIT : ballView.width: ${ballCustomView.width} , ballView.height: ${ballCustomView.height}")
        initialWidth = Math.min(displaySize.x, displaySize.y) * 0.8f
        initialHeight = Math.min(displaySize.x, displaySize.y) * 0.8f


        titleButton.setOnClickListener {
            val zoom = 1f
            ballCustomView.scale = 1f
            ballCustomView.layoutParams.width = (initialWidth * zoom).toInt()
            ballCustomView.layoutParams.height = (initialHeight * zoom).toInt()
            ballCustomView.invalidate()
            ballCustomView.requestLayout()

            seekbar.progress = 1
        }

        titleButton.setOnLongClickListener {
            ballCustomView.centerGridLine = !ballCustomView.centerGridLine
            ballCustomView.invalidate()
            true
        }

        val dummies = listOf(
            ClubTypeColorData("I3", Color.argb(255, 187, 134, 252)),
            ClubTypeColorData("I5", Color.argb(255, 98, 0, 238)),
            ClubTypeColorData("W3", Color.argb(255, 37, 84, 253)),
            ClubTypeColorData("W4", Color.argb(255, 0, 37, 170)),
            ClubTypeColorData("U6", Color.argb(255, 253, 191, 0)),
            ClubTypeColorData("PW", Color.argb(255, 147, 10, 0)),
            ClubTypeColorData("GW", Color.argb(255, 90, 5, 0)),
            ClubTypeColorData("1"),
            ClubTypeColorData("22"),
            ClubTypeColorData("35"),
            ClubTypeColorData("37"),
            ClubTypeColorData("99"),
            ClubTypeColorData("100"),
        )
        var dummyIndex = 0
        shotButton.setOnClickListener {
            dummyIndex += 1
            dummyIndex %= dummies.size
            ballCustomView.text = dummies[dummyIndex]
            ballCustomView.invalidate()
        }

        ballCustomView.setOnClickListener {
            Log.d(TAG, "centerTextView Clicked-()")
            Toast.makeText(it.context, "Club:${dummies[dummyIndex].clubTypeText}, Color:${dummies[dummyIndex].colorCode}", Toast.LENGTH_SHORT).show()
        }


        seekbar = findViewById(R.id.seekbar)
        seekbar.setOnSeekBarChangeListener(listener)
    }

}

data class ClubTypeColorData(
    val clubTypeText : String,
    val colorCode: Int = Color.argb(255, 170, 185, 190)
)
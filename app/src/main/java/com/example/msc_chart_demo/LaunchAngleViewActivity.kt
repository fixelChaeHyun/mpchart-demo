package com.example.msc_chart_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.msc_chart_demo.launchangle.LaunchAngleView
import com.example.msc_chart_demo.launchangle.LaunchDirectionView

class LaunchAngleViewActivity : AppCompatActivity(), View.OnClickListener {

    var buttons = listOf<Button>()

    lateinit var launchAngleView: LaunchAngleView
    lateinit var launchDirectionView: LaunchDirectionView

    private val buttonAngles = listOf<Double>(
        0.75, 6.52, 16.3, 32.6
    )

    private val buttonDirections = listOf<Double>(
        -5.0, 9.0, -2.3, 7.3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_angle_view)


        val button1 = findViewById<Button>(R.id.btnAngle1).apply { text = buttonAngles[0].toString() }
        val button2 = findViewById<Button>(R.id.btnAngle2).apply { text = buttonAngles[1].toString() }
        val button3 = findViewById<Button>(R.id.btnAngle3).apply { text = buttonAngles[2].toString() }
        val button4 = findViewById<Button>(R.id.btnAngle4).apply { text = buttonAngles[3].toString() }

        val button5 = findViewById<Button>(R.id.btnAngle5).apply { text = buttonDirections[0].toString() }
        val button6 = findViewById<Button>(R.id.btnAngle6).apply { text = buttonDirections[1].toString() }
        val button7 = findViewById<Button>(R.id.btnAngle7).apply { text = buttonDirections[2].toString() }
        val button8 = findViewById<Button>(R.id.btnAngle8).apply { text = buttonDirections[3].toString() }

        buttons = listOf(
            button1, button2, button3, button4,
            button5, button6, button7, button8
        ).apply { map{it.setOnClickListener(this@LaunchAngleViewActivity)} }

        launchAngleView = findViewById(R.id.launchAngleView)
        launchDirectionView = findViewById(R.id.launchDirectionView)

    }

    override fun onClick(v: View?) {
        if (v == null) return
        var launchAngle: Double = 0.0
        var launchDirection: Double = 0.0
        var colorCode: Int = v.context.resources.getColor(R.color.blue)
        when (v.id) {
            R.id.btnAngle1 -> {
                // 0.0도
                colorCode = v.context.resources.getColor(R.color.blue)
                launchAngle = buttonAngles[0]
            }
            R.id.btnAngle2 -> {
                // 5.5도
                colorCode = v.context.resources.getColor(R.color.red)
                launchAngle = buttonAngles[1]
            }
            R.id.btnAngle3 -> {
                // 12.5도
                colorCode = v.context.resources.getColor(R.color.green)
                launchAngle = buttonAngles[2]
            }
            R.id.btnAngle4 -> {
                // 23도
                colorCode = v.context.resources.getColor(R.color.purple_200)
                launchAngle = buttonAngles[3]
            }
            R.id.btnAngle5 -> {
                colorCode = v.context.resources.getColor(R.color.purple_200)
                launchDirection = buttonDirections[0]

            }
            R.id.btnAngle6 -> {
                colorCode = v.context.resources.getColor(R.color.red)
                launchDirection = buttonDirections[1]

            }
            R.id.btnAngle7 -> {
                colorCode = v.context.resources.getColor(R.color.green)
                launchDirection = buttonDirections[2]

            }
            R.id.btnAngle8 -> {
                colorCode = v.context.resources.getColor(R.color.blue)
                launchDirection = buttonDirections[3]

            }
        }
        if (launchAngle != 0.0) {
            launchAngleView.updateView(launchAngle, colorCode)
        }
        if (launchDirection != 0.0) {
            launchDirectionView.updateView(launchDirection, colorCode)
        }
    }
}
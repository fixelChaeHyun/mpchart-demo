package com.example.msc_chart_demo

import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class Circle2Activity : AppCompatActivity() {
    lateinit var arcView: ArcView7
    lateinit var seekbar : SeekBar
    lateinit var button : Button
    lateinit var buttonShot : Button
    lateinit var textDistance : TextView

    lateinit var linearLayout: LinearLayout
    lateinit var ballCustomView: BallCustomView
    lateinit var testLayout: LinearLayout
    lateinit var constraintLayout: ConstraintLayout
    lateinit var constraintSet: ConstraintSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle2)

        constraintLayout = findViewById(R.id.constraintLayout)
        constraintSet = ConstraintSet()

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

        linearLayout = findViewById(R.id.linearLayout)

        testLayout = LinearLayout(this)
        testLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        testLayout.orientation = LinearLayout.VERTICAL
        testLayout.setBackgroundColor(Color.argb(30,250, 250, 250))



        testLayout.requestLayout()

        Log.i("Circle2Activity", "testLayout.size : width: ${testLayout.layoutParams.width} , height: ${testLayout.layoutParams.height} / linear.width: ${linearLayout.layoutParams.width}, linear.height: ${linearLayout.layoutParams.height} / arcView.width: ${arcView.layoutParams.width}, arcView.height: ${arcView.layoutParams.height}")

        var ballSize = 65
        ballCustomView = BallCustomView(context = this, null)
        ballCustomView.scale = 0.08f
        ballCustomView.layoutParams = LinearLayout.LayoutParams(ballSize, ballSize)
        ballCustomView.setOnClickListener {
            Toast.makeText(this, "Ball1.클럽명:${ballCustomView.text.clubTypeText}", Toast.LENGTH_SHORT).show()
        }
        ballCustomView.x = linearLayout.layoutParams.width / 2f
        ballCustomView.y = linearLayout.layoutParams.height / 2f


        val distance = 150
        val rad = 15 - 90

        val distanceRatio = linearLayout.layoutParams.height / 2f
        val distanceHeight = 150f
        val distanceRatioWidth = linearLayout.layoutParams.width / 2f
        var result = distanceRatio * distance / distanceHeight
        var resultWidth = distanceRatioWidth * distance / distanceHeight

        val endY = linearLayout.layoutParams.height

        val x = resultWidth * cos(Math.toRadians(rad.toDouble()))
        val y = result * sin(Math.toRadians(rad.toDouble()))

        val ballDistanceX = x.toFloat() + linearLayout.layoutParams.width / 2f
        val ballDistanceY = y.toFloat() + endY

        ballCustomView.x = ballDistanceX
        ballCustomView.y = ballDistanceY
        ballCustomView.requestLayout()
        linearLayout.addView(ballCustomView)

        Toast.makeText(this, "ballX: $ballDistanceX, ballY: $ballDistanceY", Toast.LENGTH_SHORT).show()

        val ballCustomView2 = BallCustomView(this, null).apply {
            text = ClubTypeColorData("W3", Color.argb(255, 37, 84, 253))
            scale = 0.06f
            layoutParams = LinearLayout.LayoutParams(ballSize, ballSize)
            ballCustomView.requestLayout()
            setOnClickListener {
                Toast.makeText(it.context, "Ball2.클럽명: ${text.clubTypeText}", Toast.LENGTH_SHORT).show()
            }
            this.x = linearLayout.layoutParams.width / 2f - 30 -100
            this.y = linearLayout.layoutParams.height / 2f - 30 -200
        }

        linearLayout.addView(ballCustomView2)

        val ballCustomView3 = BallCustomView(this, null).apply {
            text = ClubTypeColorData("W6", Color.argb(255, 70, 120, 253))
            scale = 0.08f
            layoutParams = LinearLayout.LayoutParams(ballSize, ballSize)
            ballCustomView.requestLayout()
            setOnClickListener {
                Toast.makeText(it.context, "Ball3.클럽명: ${text.clubTypeText}", Toast.LENGTH_SHORT).show()
            }
            this.x = linearLayout.layoutParams.width / 2f - 30 + 150
            this.y = linearLayout.layoutParams.height / 2f - 30 -200
        }

        testLayout.addView(ballCustomView3)
//        linearLayout.addView(ballCustomView3)
//        testLayout.requestLayout()
        // 회색 레이아웃 -> ArcView 똑같은 크기로 만듬.
        val layoutParam = ConstraintLayout.LayoutParams(
            arcView.layoutParams.width,
            arcView.layoutParams.height
        )
        layoutParam.topToTop = constraintLayout.id
        layoutParam.rightToRight = constraintLayout.id
        layoutParam.leftToLeft = constraintLayout.id
        layoutParam.bottomToBottom = constraintLayout.id


        testLayout.layoutParams = layoutParam

        constraintLayout.addView(testLayout)
//        addContentView(testLayout, layoutParam)

//        val constParam = layoutParam as ConstraintLayout.LayoutParams
//        constParam.leftToLeft = ConstraintSet.PARENT_ID
//        constParam.startToStart = constraintLayout.id
//        constParam.topToTop = constraintLayout.id
//        constParam.bottomToBottom = ConstraintSet.PARENT_ID
//        testLayout.layoutParams = constParam
        testLayout.requestLayout()
        constraintLayout.requestLayout()
//
//        constraintSet.clone(constraintLayout)
//        constraintSet.clear(constraintLayout.id)
//        constraintSet.connect(testLayout.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
//        constraintSet.connect(testLayout.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
//        constraintSet.connect(testLayout.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
//        constraintSet.applyTo(constraintLayout)

    }
}
package com.example.msc_chart_demo

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast

/**
 * 페어웨이를 바닥에 그릴때 사용하는 뷰 하나,
 * 타구를 그릴 레이아웃 하나
 * 타구를 나타내는 볼 커스텀 뷰 하나
 * 총 3가지 뷰를 하나의 레이아웃으로 묶어서 사용할 수 있도록 라이브러리화 진행필요
 * */
class CustomArcLayout : FrameLayout {
    private val TAG = this::class.java.simpleName

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    lateinit var customArcView: CustomArcView
    lateinit var ballDrawLayout: FrameLayout//LinearLayout
//    lateinit var ballCustomView: BallCustomView

    private var showGridLine: Boolean = false
    private var showValidShotArea = false

    // Ball Data
    private val shotData : ArrayList<BallShotData> = arrayListOf()

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.layout_custom_arc, this, true)
        customArcView = findViewById(R.id.customArcView)
        ballDrawLayout = findViewById(R.id.ballDrawLayout)

        // CustomArcView 그리기 - Init
        customArcView.invalidate()
    }

    fun showFairwayGridLine() {
        showGridLine = !showGridLine
        customArcView.showGridLine = showGridLine
        customArcView.invalidate()
    }

    fun showValidShotArea() {
        showValidShotArea = !showValidShotArea
        customArcView.showValidShotArea = showValidShotArea
        customArcView.invalidate()
    }

    // Params : ShotData
    fun makeShot() {
        // ballDrawLayout 위에 ball 을 그려주고 onClick Event 를 받을 수 있어야함.
        val dummies = listOf<BallShotDataNew>(
            BallShotDataNew(250f, 10.0, "W3", Color.argb(255, 50, 120, 190)),
            BallShotDataNew(250f, 10.0, "W4", Color.argb(255, 40, 110, 175)),
            BallShotDataNew(250f, 10.0, "W6", Color.argb(255, 30, 100, 150)),
            BallShotDataNew(250f, 10.0, "W9", Color.argb(255, 20, 90, 135)),

            BallShotDataNew(233f, 0.5, "I3", Color.argb(255, 200, 100, 55)),
            BallShotDataNew(233f, 0.5, "I4", Color.argb(255, 180, 90, 40)),
            BallShotDataNew(123f, -3.0, "I5", Color.argb(255, 160, 80, 25)),
            BallShotDataNew(233f, 0.5, "I6", Color.argb(255, 140, 70, 10)),
            BallShotDataNew(248f, -0.5, "I7", Color.argb(255, 120, 60, 0)),

            BallShotDataNew(178f, -10.5, "U3", Color.argb(255, 224, 45, 207)),

            BallShotDataNew(178f, -10.5, "13", Color.argb(255, 160, 200, 200)),
            BallShotDataNew(178f, -10.5, "97", Color.argb(255, 160, 200, 200)),
            BallShotDataNew(178f, -10.5, "100", Color.argb(255, 160, 200, 200))
        )

        val randomIdx = (dummies.indices).random()
        val dummySingleBallData = listOf(dummies[randomIdx])

        val ballSize = 45
        val ballScale = 0.05f

        for (dummy in dummySingleBallData) {
            var testBall = dummy
            val randomRange = ((ballDrawLayout.width*0.3).toInt()*-1..(ballDrawLayout.width*0.3).toInt())
            val randomRangeY = ((ballDrawLayout.height*0.4).toInt()..(ballDrawLayout.height*0.9).toInt())
            val offsetX = randomRange.random()
            val offsetY = randomRangeY.random()
            val clubTypeColorData = ClubTypeColorData(testBall.clubType, testBall.color)

            val newRandomRangeY = ((ballDrawLayout.height*0.1).toInt()..(ballDrawLayout.height*0.4).toInt())
            val newRandomRangeX = ((ballDrawLayout.width*0.3).toInt()..(ballDrawLayout.width*0.7).toInt())
            val moveOffsetX = newRandomRangeX.random()
            val moveOffsetY = newRandomRangeY.random()

//            Toast.makeText(context, "randomIdx : $randomIdx, offX: $offsetX, offY: $offsetY", Toast.LENGTH_SHORT).show()
            Log.w(TAG, "randomIdx : $randomIdx, offX: $offsetX, offY: $offsetY")

            var ballCustomView = BallCustomView(context, null).apply {
                text = clubTypeColorData
                scale = ballScale
                layoutParams = LayoutParams(ballSize, ballSize) //LinearLayout.LayoutParams(ballSize, ballSize)
//                this.x = customArcView.width / 2f - ballSize/2f  - offsetX
//                this.y = customArcView.height / 2f - ballSize/2f - offsetY
                this.x = customArcView.width/2f - ballSize/2f
                this.y = customArcView.height.toFloat() - ballSize/2f
                Log.e(
                    "CustomArcLayout",
                    "clubType: ${testBall.clubType} , x: ${this.x} , y: ${this.y}"
                )
                setOnClickListener {
                    Toast.makeText(
                        context,
                        "ClubType: ${text.clubTypeText} , x: ${this.x} , y: ${this.y}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(
                        "CustomArcLayout",
                        "clubType: ${testBall.clubType} , x: ${this.x} , y: ${this.y}, arcView.width: ${customArcView.width/2f}, arcView.height: ${customArcView.height/2f}"
                    )
                }
            }

            val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.duration = 1200

            animator.addUpdateListener {
                val updateValue: Float = it.animatedFraction
                ballCustomView.x = customArcView.width/2f -ballSize/2f - offsetX * updateValue
                ballCustomView.y = customArcView.height - ballSize/2f - offsetY * updateValue
//                ballDrawLayout.invalidate()
                Log.d("CustomArcLayout", "updateValue -> x: ${customArcView.width/2f - offsetX * updateValue}, y: ${customArcView.height - offsetY * updateValue}")
            }
            animator.start()
            /*
            ballCustomView.animate()
                .setInterpolator(AccelerateDecelerateInterpolator())
//                .x(customArcView.width/2f - ballSize/2f - offsetX)
//                .y(customArcView.height.toFloat() - ballSize/2f - offsetY)
//                .xBy(customArcView.width / 2f - ballSize/2f  - offsetX)
//                .yBy(customArcView.height / 2f - ballSize/2f - offsetY)
                .x(moveOffsetX.toFloat())
                .y(moveOffsetY.toFloat())
                .setDuration(1200)
                .withStartAction {
                Log.e(TAG, "START!")
            }
                .withEndAction {
                    Log.e(TAG, "END!")
                }
                .start()

             */
            ballDrawLayout.addView(ballCustomView) //, ballDrawLayout.childCount
            Log.e(TAG, "ballDrawLayout.addView -> childCount: ${ballDrawLayout.childCount}")
        }
        ballDrawLayout.requestLayout()
    }

    fun clearShotLayout() {
        val childFirst = ballDrawLayout.getChildAt(0)
        Log.e(TAG, "childCount: ${ballDrawLayout.childCount}, child[0]: $childFirst, ${ballDrawLayout.indexOfChild(ballDrawLayout.getChildAt(0))} ,")
        ballDrawLayout.removeAllViewsInLayout()
    }


    fun removeLastShotView() {
        ballDrawLayout.removeViewAt(ballDrawLayout.childCount-1)
    }

    fun removeShotView(index: Int) {
        ballDrawLayout.removeViewAt(index)
    }

}
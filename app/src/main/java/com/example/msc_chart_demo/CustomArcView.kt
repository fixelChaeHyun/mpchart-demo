package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlin.math.cos
import kotlin.math.sin

class CustomArcView : View {
    private val TAG = this::class.java.simpleName

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var showGridLine: Boolean = false
    var showValidShotArea = false

    var viewScale: Float = 1f
    var zoomScale: Float = 1f
    var rectF : RectF = RectF(0f, 0f, 0f, 0f)
    var rectFrame : RectFrame = RectFrame(0f, 0f, 0f, 0f, 0f, 0f, false)

    val intervalArcLine = 0.16f

    var testLayout: LinearLayout = LinearLayout(context)

    init {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        testLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        testLayout.orientation = LinearLayout.VERTICAL
        testLayout.setBackgroundColor(Color.argb(255, 1, 1, 1))
        testLayout.requestLayout()

    }

    private val viewBoundaryLinePaint = Paint().apply {
        strokeWidth = 3f
        color = Color.parseColor(Const.colorBlue)
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val viewGuideLinePaint = Paint().apply {
        strokeWidth = 2f
        color = Color.parseColor(Const.colorBlue)
        style = Paint.Style.STROKE
        isAntiAlias = true
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    private val viewGuideSolidLinePaint = Paint().apply {
        strokeWidth = 2f
        color = Color.parseColor(Const.colorYellow)
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val verticalGuideLinePaint = Paint().apply {
        strokeWidth = 2f
        color = Color.parseColor(Const.colorYellow)
        style = Paint.Style.STROKE
        isAntiAlias = true
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
    }

    private val blackDotPaint = Paint().apply {
        color = Color.rgb(0,0,0)
        style = Paint.Style.FILL_AND_STROKE
        alpha = 200
    }

    private val teeBoxPaint = Paint().apply {
        color = Color.parseColor(Const.greenColor)
        style = Paint.Style.FILL_AND_STROKE
        alpha = 200
    }

    private val teeBoxInnerPaint = Paint().apply {
        color = Color.parseColor(Const.colorOrange)
        style = Paint.Style.FILL_AND_STROKE
        alpha = 230
    }



    private val textSizeForDistance: Float = 30f
    val distanceTextStyle = Paint().apply {
        strokeWidth = 0.5f
        color = Color.parseColor(Const.colorWhite)
        style = Paint.Style.FILL_AND_STROKE
        textSize = textSizeForDistance * viewScale
        textAlign = Paint.Align.CENTER
    }

    val graphArcLine = Paint().apply {
        strokeWidth = 3f
        color = Color.parseColor(Const.colorWhite)
        style = Paint.Style.STROKE
    }

    val centerLinePaint = Paint().apply {
        strokeWidth = 1f
        color = Color.parseColor(Const.colorWhite)
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
    }

    val validAreaPaint = listOf<Paint>(
        Paint().apply {
            color = Color.parseColor(Const.colorYellow)
            style = Paint.Style.FILL
            alpha = 70
            isAntiAlias = true
        },
        Paint().apply {
            color = Color.parseColor(Const.colorPurple)
            style = Paint.Style.FILL_AND_STROKE
            alpha = 150
            isAntiAlias = true
        }
    )



    /** Activity 에서 CustomView 의 사이즈를 강제로 변경 시켰을 때 값 확인해보기 */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.w(TAG, "onSizeChanged() -> w: $w, h: $h, oldW: $oldw, oldH: $oldh")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // CircleBallImageView
        val minw = paddingLeft + paddingRight + suggestedMinimumWidth
        val w = resolveSizeAndState(minw, widthMeasureSpec, 1)

        // Whatever the width ends up being, ask for a height that would let the pie get as big as it can
        val minh = paddingTop + paddingBottom + MeasureSpec.getSize(w)
        val h: Int = resolveSizeAndState(minh, heightMeasureSpec, 0)

        Log.i(TAG, "onMeasure() -> suggestedMinWidth: $suggestedMinimumWidth, suggestedMinHeight: $suggestedMinimumHeight")
        Log.i(TAG, "onMeasure() -> paddingLeft: $paddingLeft, paddingTop: $paddingTop, paddingRight: $paddingRight, paddingBottom: $paddingBottom")
        Log.i(TAG, "onMeasure() -> w: $w, minW: $minw, h: $h, minH: $minh")

        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "onLayout() -> changed: $changed, left: $left, top: $top, right: $right, bottom: $bottom \nwidth: ${right-left}, height: ${bottom-top}")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        if (width < 0 || height < 0) {
            Log.e(TAG, "Error : The width or height cannot be zero.")
        }

        // 세로 / 가로 비율 계산
        var heightRatio: Float = height.toFloat() / width.toFloat()
        val calculatedWidth = width * zoomScale
        val calculatedHeight = height * zoomScale

        val minusWidth = width.toFloat() - calculatedWidth
        val minusHeight = height.toFloat() - calculatedHeight




        val leftTopX = 0f + minusWidth / 2
        val leftTopY = 0f + minusHeight / 2

        val endX = width.toFloat()
        val endY = height.toFloat()

        val centerX = (leftTopX + width) / 2f
        val centerY = (leftTopY + height) / 2f

        val teeBoxX = width.toFloat() / 2
        val teeBoxY = height.toFloat()


        // offsetWidth
        val offsetValue = (calculatedWidth * intervalArcLine).toFloat()
        val offsetHeight = ((calculatedHeight * heightRatio) * intervalArcLine).toFloat()

        Log.e(TAG, "offset 확인: offsetHeight(50간격): $offsetHeight , teeBoxY: $teeBoxY , point: ${teeBoxY-offsetHeight}")
//        canvas.drawCircle(teeBoxX, (teeBoxY-offsetHeight), 20f, blackDotPaint)        // 50 위치에 점 찍어서 확인


        if (showGridLine) {
            val dotSize = 10f
            canvas.drawCircle(leftTopX, leftTopY, dotSize, blackDotPaint)
            canvas.drawCircle(endX, leftTopY, dotSize, blackDotPaint)
            canvas.drawCircle(leftTopX, endY, dotSize, blackDotPaint)
            canvas.drawCircle(endX, endY, dotSize, blackDotPaint)

            canvas.drawLine(leftTopX, leftTopY, endX, leftTopY, viewBoundaryLinePaint)
            canvas.drawLine(leftTopX, leftTopY, leftTopX, endY, viewBoundaryLinePaint)
            canvas.drawLine(leftTopX, endY, endX, endY, viewBoundaryLinePaint)
            canvas.drawLine(endX, leftTopY, endX, endY, viewBoundaryLinePaint)

            canvas.drawLine(leftTopX, leftTopY, endX, endY, viewGuideLinePaint)
            canvas.drawLine(endX, leftTopY, leftTopX, endY, viewGuideLinePaint)
            canvas.drawCircle((endX - leftTopX)/2f, (endY-leftTopY)/2f, dotSize, blackDotPaint)

            canvas.drawLine(width / 2f, endY, leftTopX, leftTopY, viewGuideSolidLinePaint)
            canvas.drawLine(width / 2f, endY, endX, leftTopY, viewGuideSolidLinePaint)

            // 비율대로 칸 나누기
            var cnt = 5
            var verticalLine = teeBoxY
            while (cnt-- > 0) {
                verticalLine -= offsetHeight
                canvas.drawLine(leftTopX, verticalLine, endX, verticalLine, verticalGuideLinePaint)
            }
        }

        drawTeeBox(canvas, teeBoxX, teeBoxY, 32f*viewScale, 70f*viewScale)


        // Draw Arc Lines
        val startAngleInput = 65f
        val endAngleInput = 115f

        val startAngle = 360f - endAngleInput
        val sweepAngle = endAngleInput - startAngleInput

        // Draw Arc Line 15, 30
        val distanceTxt = listOf("15", "30")
        val distance15X = 15 * offsetValue / 50
        val distance15Y = 15 * offsetHeight / 50
        rectFrame = rectFrame.copy(
            left = teeBoxX - distance15X,
            top = teeBoxY - distance15Y,
            right = teeBoxX + distance15X,
            bottom = teeBoxY + distance15Y,
            startAngle = startAngle,
            sweepAngle = sweepAngle
        )

        canvas.drawArc(rectFrame.left, rectFrame.top, rectFrame.right, rectFrame.bottom, rectFrame.startAngle, rectFrame.sweepAngle, false, graphArcLine)

        rectFrame = rectFrame.copy(
            left = rectFrame.left - distance15X,
            top = rectFrame.top - distance15Y,
            right = rectFrame.right + distance15X,
            bottom = rectFrame.bottom + distance15Y,
            startAngle = startAngle,
            sweepAngle = sweepAngle
        )

        canvas.drawArc(rectFrame.left, rectFrame.top, rectFrame.right, rectFrame.bottom, rectFrame.startAngle, rectFrame.sweepAngle, false, graphArcLine)

        val distanceText = listOf("50", "100", "150", "200", "250", "300")
        rectFrame = rectFrame.copy(
            left = teeBoxX - offsetValue,
            top = teeBoxY - offsetHeight,
            right = teeBoxX + offsetValue,
            bottom = teeBoxY + offsetHeight,
            startAngle = startAngle,
            sweepAngle = sweepAngle
        )
        var count = 6
        var start = 0
        while (start < count) {
            canvas.drawArc(rectFrame.left, rectFrame.top, rectFrame.right, rectFrame.bottom, rectFrame.startAngle, rectFrame.sweepAngle, false, graphArcLine)

            val rad = -25.0
            val arcHeight = (rectFrame.bottom - rectFrame.top) / 2f
            val textX = arcHeight * cos(Math.toRadians((rad-90.0))) + teeBoxX - (60*viewScale)
            val textY = arcHeight * sin(Math.toRadians((rad-90.0))) + teeBoxY + (10*viewScale)
            Log.e(TAG, "textX : $textX , textY: $textY")
            distanceTextStyle.textSize = textSizeForDistance * viewScale
            canvas.drawText(distanceText[start], textX.toFloat(), textY.toFloat(), distanceTextStyle)
            if (++start == count) break
            rectFrame = rectFrame.copy(
                left = rectFrame.left - offsetValue,
                top = rectFrame.top - offsetHeight,
                right = rectFrame.right + offsetValue,
                bottom = rectFrame.bottom + offsetHeight
            )
        }

        // 가운데 선 그리기
        val centerLineOverSize = 30 * zoomScale * viewScale
        canvas.drawLine(teeBoxX, teeBoxY, rectFrame.left+(rectFrame.right- rectFrame.left)/2, rectFrame.top-centerLineOverSize, centerLinePaint)


        if (showValidShotArea) {
            // 각도 그리기
//            canvas.drawCircle(centerX+10, centerY+10, 10f, blackDotPaint)
            rectF.apply {
                left = rectFrame.left
                top = rectFrame.top
                right = rectFrame.right
                bottom = rectFrame.bottom
            }
            canvas.drawArc(rectF, -68f, -44f, true, validAreaPaint[0])
            canvas.drawArc(rectF, -85f, -10f, true, validAreaPaint[1])

        }

    }

    private fun drawGridLine(canvas: Canvas, startX: Float, startY: Float) {

    }

    private fun drawTeeBox(canvas: Canvas, x: Float, y: Float, innerSize: Float, outerSize: Float) {
        // Draw Tee box
        canvas.drawCircle(x, y, outerSize, teeBoxPaint)
        canvas.drawCircle(x, y, innerSize, teeBoxInnerPaint)
    }

    fun redrawArcView(newWidth: Int, newHeight: Int, scale: Float) {
        viewScale = scale
        layoutParams.width = newWidth
        layoutParams.height = newHeight
        requestLayout()

    }
}
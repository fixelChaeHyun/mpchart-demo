package com.example.msc_chart_demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.msc_chart_demo.data.CustomBallData

class CircleBallImageView : View {
    private val TAG = this::class.java.simpleName

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val defaultFontSize = 400f
    private val defaultCircleRatio = 0.95f
    var viewScale = 1f
    var fontScale = 1f
    var zoomScale = 1f
    var showGridLine = false

    var ballData: CustomBallData = CustomBallData("1")

    private val viewBoundaryLinePaint = Paint().apply {
        strokeWidth = 3f
        color = Color.parseColor(Const.colorBlue)
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val viewGuideLinePaint = Paint().apply {
        strokeWidth = 2f
        color = Color.parseColor(Const.colorYellow)
        style = Paint.Style.STROKE
        isAntiAlias = true
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    private val textPaint = Paint().apply {
        strokeWidth = 1f
        color = Color.parseColor(Const.colorWhite)
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.CENTER
        textSize = defaultFontSize * fontScale * viewScale * zoomScale
        isAntiAlias = true
    }

    private val ballPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val ballOutlinePaint = Paint().apply {
        color = Color.argb(255, 230, 230, 230)      // Gray
        style = Paint.Style.STROKE
        strokeWidth = defaultFontSize * 0.01f * viewScale * zoomScale
        isAntiAlias = true
    }

    init {
    }

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

        val startX = 0f
        val startY = 0f

        val centerX = (startX + width) / 2f
        val centerY = (startY + height) / 2f

        // Circle 그리기
        val ballRadius = (centerX - startX) * defaultCircleRatio * zoomScale
        ballPaint.color = ballData.colorCode
        canvas.drawCircle(centerX, centerY, ballRadius, ballPaint)

        if (ballData.showOutline) {
            ballOutlinePaint.strokeWidth = defaultFontSize * 0.01f * viewScale * zoomScale
            canvas.drawCircle(centerX, centerY, ballRadius, ballOutlinePaint)
        }

        // Text 그리기
        textPaint.textSize = defaultFontSize * fontScale * viewScale * zoomScale
        val fontMetrics: Paint.FontMetrics = textPaint.fontMetrics
        Log.d(TAG, "FontMetrics > top: ${fontMetrics.top}, bottom: ${fontMetrics.bottom}, ascent: ${fontMetrics.ascent}, descent: ${fontMetrics.descent}, leading: ${fontMetrics.leading}")
        val fontHeight = fontMetrics.bottom - fontMetrics.top
        val distance = fontHeight/2 - fontMetrics.bottom
        canvas.drawText(ballData.clubTypeText, centerX, centerY + distance, textPaint)



        if (showGridLine) {
            canvas.drawLine(startX, startY, startX + width, startY, viewBoundaryLinePaint)
            canvas.drawLine(startX, startY, startX, startY + height, viewBoundaryLinePaint)
            canvas.drawLine(width.toFloat(), height.toFloat(), startX, startY + height, viewBoundaryLinePaint)
            canvas.drawLine(width.toFloat(), height.toFloat(), startX + width, startY, viewBoundaryLinePaint)

            // 가운데 격자선
            canvas.drawLine(centerX, startY, centerX, startY + height, viewGuideLinePaint)
            canvas.drawLine(startX, centerY, startX + width, centerY, viewGuideLinePaint)
        }

    }

    fun toggleGridLine() {
        showGridLine = !showGridLine
        invalidate()
    }
}
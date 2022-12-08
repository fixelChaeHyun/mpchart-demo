package com.example.msc_chart_demo.launchangle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class LaunchAngleView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val TAG = this.javaClass.simpleName

    var fillPercent = 0.99f

    val basePath: Path = Path()

    var viewAngle : Double = 0.0

    private val paintStyleBlack = Paint().apply {
        strokeWidth = 3f
        color = Color.parseColor("#000000")
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val paintStyleArrow = Paint().apply {
        strokeWidth = 3f
        color = Color.parseColor("#000000")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val paintStyleTextBlack = Paint().apply {
        strokeWidth = 1f
        color = Color.parseColor("#000000")
        style = Paint.Style.FILL_AND_STROKE
        textSize = 40f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    var gridForVerticalBar = Paint().apply {
        strokeWidth = 1.5f
        color = Color.parseColor("#000000")
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
        isAntiAlias = true
        alpha = 200
    }

    private val paintStyleArc = Paint().apply {
        strokeWidth = 5f
        color = Color.parseColor("#EA002B")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val outlineOffset = 3f
        val widthScaled = width * fillPercent
        val heightScaled = height * fillPercent
        Log.d(TAG, " - width : $width , height : $height\nwScaled: $widthScaled, hScaled: $heightScaled")

        val nw = widthScaled - outlineOffset
        val nh = heightScaled - outlineOffset

        canvas.drawLine(outlineOffset, nh, outlineOffset, outlineOffset, paintStyleBlack)  // 좌측세로선
//        canvas.drawLine(outlineOffset, nh, nw, nh, paintStyleBlack) // 바닥 가로선

        // LaunchAngle 의 시작 꼭지점 Point (outlineOffset, nh)
//        canvas.drawCircle(outlineOffset, nh, 10f, paintStyleTextBlack)

        val lineLength = nw * 0.65
        val angle = viewAngle

        val left = outlineOffset - (lineLength)
        val top = nh - (lineLength)
        val right = outlineOffset + (lineLength)
        val bottom = nh + (lineLength) + outlineOffset
        val rect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

        canvas.drawArc(rect, 0f, angle.toFloat(), true, paintStyleArc)


        val lineLengthArrow = lineLength * 1.2
        val x = cos(Math.toRadians(angle)) * lineLengthArrow
        val y = sin(Math.toRadians(angle)) * lineLengthArrow
        Log.d(TAG, " -> x: $x, y: $y  -> convertX: ${x + outlineOffset}, convertY: ${y + nh}")
        Log.d(TAG, " -> sX: $outlineOffset, sY: $nh")

        canvas.drawLine(outlineOffset, nh, (outlineOffset + x).toFloat(), (nh + y).toFloat(), paintStyleArrow)


        // 삼각형 꼭지점 1
        val x1 = (outlineOffset + x * 1.07f).toFloat()
        val y1 = (nh + y * 1.07f).toFloat()
//        canvas.drawCircle(x1, y1, 5f, paintStyleTextBlack)

        val str = "${abs(viewAngle)}°"
        canvas.drawText(str, x1+30, y1-30, paintStyleTextBlack)

        // 삼각형 꼭지점 2
        val upperX = cos(Math.toRadians(angle-2.0)) * (lineLengthArrow)
        val upperY = sin(Math.toRadians(angle-2.0)) * (lineLengthArrow)

        // 삼각형 꼭지점 3
        val lowerX = cos(Math.toRadians(angle+2.0)) * (lineLengthArrow)
        val lowerY = sin(Math.toRadians(angle+2.0)) * (lineLengthArrow)

//        canvas.drawCircle(upperX.toFloat() + outlineOffset, upperY.toFloat() + nh, 5f, paintStyleTextBlack)
//        canvas.drawCircle(lowerX.toFloat() + outlineOffset, lowerY.toFloat() + nh, 5f, paintStyleTextBlack)

        val x2 = (upperX + outlineOffset).toFloat()
        val y2 = (upperY + nh).toFloat()
        val x3 = (lowerX + outlineOffset).toFloat()
        val y3 = (lowerY + nh).toFloat()

        basePath.apply {
            fillType = Path.FillType.EVEN_ODD
            moveTo(x1, y1)
            lineTo(x2, y2)
            lineTo(x3, y3)
            lineTo(x1, y1)
            close()
        }
        canvas.drawPath(basePath, paintStyleArrow)
        basePath.reset()

        canvas.drawLine(outlineOffset, nh, nw, nh, paintStyleBlack) // 바닥 가로선
    }

    fun updateView(angle: Double, colorCode: Int) {
        Log.e(TAG, "updateView-() -> Angle: $angle")
        viewAngle = angle * -1
        paintStyleArc.color = colorCode
        invalidate()
    }
}
package com.example.msc_chart_demo.launchangle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.msc_chart_demo.Const
import java.lang.Math.cos
import java.lang.Math.sin

class LaunchDirectionView : View {
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
        strokeWidth = 0.5f
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

        val widthHalf = widthScaled / 2f + outlineOffset
        val nw = widthScaled - outlineOffset
        val nh = heightScaled - outlineOffset
        val zeroW = outlineOffset
        val zeroH = outlineOffset

        canvas.drawLine(widthHalf, zeroH, widthHalf, nh, paintStyleBlack) // 세로 중심선
        canvas.drawLine(zeroW, nh, nw, nh, paintStyleBlack) // 바닥 가로선

        val str = "$viewAngle°"
        canvas.drawText(str, nw-50, nh-20, paintStyleTextBlack)

        // 부채꼴 시작지점 꼭지점
        canvas.drawCircle(widthHalf, nh, 10f, paintStyleTextBlack)

        val lineLength = nw * 0.65
        val angle = viewAngle

        val left = widthHalf - lineLength
        val top  = nh - lineLength
        val right = widthHalf + lineLength
        val bottom = nh + lineLength
        val rect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

        Log.e(TAG, "--> l: $left, t: $top, r: $right, b: $bottom")

        canvas.drawArc(rect, -90f, angle.toFloat(), true, paintStyleArc)

        basePath.reset()
        val lineLengthArrow = lineLength * 1.2
        val x = cos(Math.toRadians(angle-90)) * lineLengthArrow
        val y = sin(Math.toRadians(angle-90)) * lineLengthArrow
        Log.d(TAG, "--> x: $x, y: $y  -> convertX: ${x + outlineOffset}, convertY: ${y + nh}")

        canvas.drawLine(widthHalf, nh, widthHalf + x.toFloat(), nh + y.toFloat(), paintStyleArrow)

        val x1 = (widthHalf + x * 1.07f).toFloat()
        val y1 = (nh + y * 1.07f).toFloat()

        val upperX = cos(Math.toRadians(angle-90-2.0)) * lineLengthArrow
        val upperY = sin(Math.toRadians(angle-90-2.0)) * lineLengthArrow

        val lowerX = cos(Math.toRadians(angle-90+2.0)) * lineLengthArrow
        val lowerY = sin(Math.toRadians(angle-90+2.0)) * lineLengthArrow

        val x2 = (upperX + widthHalf).toFloat()
        val y2 = (upperY + nh).toFloat()

        val x3 = (lowerX + widthHalf).toFloat()
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

    }

    fun updateView(angle: Double, colorCode: Int) {
        Log.e(TAG, "updateView-() -> Angle: $angle")
        viewAngle = angle
        paintStyleArc.color = colorCode
        invalidate()
    }

}
package com.example.msc_chart_demo

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

object DrawUtil {

    fun drawTeeBox(canvas: Canvas, centerX: Float, centerY: Float, innerRadius: Float, outerRadius: Float, drawCenterLine: Boolean = false, baselineTopY: Float = 0f) {
        canvas.drawCircle(centerX, centerY, outerRadius, Const.teeBoxStyle)
        canvas.drawCircle(centerX, centerY, innerRadius, Const.teeBoxInnerStyle)
        if (drawCenterLine)
            canvas.drawLine(centerX, baselineTopY, centerX, centerY, Const.centerLineStyle)
    }

    fun drawArcLine(canvas: Canvas, rect: RectFrame, distanceText: String, paintArcOption: Paint = Const.graphArcLine, paintTextOption: Paint = Const.distanceTextStyle) {
        val frame = RectF(rect.left, rect.top, rect.right, rect.bottom)
        canvas.drawArc(frame, rect.startAngle, rect.sweepAngle, rect.useCenter, paintArcOption)

        // distance Text
        val width = rect.right - rect.left
        val height = rect.bottom - rect.top
        val offsetLeft: Float = ((rect.left * 0.8) + (width * 0.17)).toFloat()
        val offsetTop: Float = (rect.top + height * 0.07).toFloat()
        canvas.drawText(distanceText, offsetLeft, offsetTop, paintTextOption)
    }

    fun drawArcLine2(canvas: Canvas, rect: RectFrame, distanceText: String, paintArcOption: Paint = Const.graphArcLine, paintTextOption: Paint = Const.distanceTextStyle) {
        val frame = RectF(rect.left, rect.top, rect.right, rect.bottom)
        canvas.drawArc(frame, rect.startAngle, rect.sweepAngle, rect.useCenter, paintArcOption)

        // distance Text
        val width = rect.right - rect.left
        val height = rect.bottom - rect.top
        val offsetLeft: Float = ((rect.left * 0.87) + (width * 0.215)).toFloat()
        val offsetTop: Float = (rect.top + height * 0.04).toFloat()
        canvas.drawText(distanceText, offsetLeft, offsetTop, paintTextOption)
    }
}
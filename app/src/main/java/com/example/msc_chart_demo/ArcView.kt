package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class ArcView constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {

   val lineColorMiddle = "#FDC6C6"
   val lineColorSide = "#A8BBFF"
   val fillColorMiddle = "#FFA833"
   val fillColorSide = "#33E3FF"

   init {

   }

   var fillPercentMiddle = 0.10f

   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)

      val paintMiddle = Paint()
      paintMiddle.strokeWidth = 9f
      paintMiddle.color = Color.parseColor(lineColorMiddle)
      paintMiddle.style = Paint.Style.STROKE
      paintMiddle.pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)

      val paintFillMiddle = Paint().apply {
         color = Color.parseColor(fillColorMiddle)
         style = Paint.Style.FILL
      }


//      val fillPercentMiddle = 0.01f
      var offset = 11f
      var rectMiddle = RectF(100f, 100f, 1000f, 1000f)
      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintMiddle)
      // RectMiddle 에 Padding 을 넣을 수 있는 방법?

      val fillX = 100f * fillPercentMiddle
      val fillY = 100f * fillPercentMiddle
      rectMiddle.set(100f + fillX, 100 + fillY, 1000f - fillY, 1000f - fillY)
      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintFillMiddle)

      val paintSide = Paint().apply {
         strokeWidth = 6f
         color = Color.parseColor(lineColorSide)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(15f, 10f), 0f)
      }



      val rectLeft = RectF(100f - offset, 100f + offset - 2, 1000f - offset, 1000f)
      canvas!!.drawArc(rectLeft, -165f, 45f, true, paintSide)
      val rectRight = RectF(100f + offset, 100f + offset- 2, 1000f + offset, 1000f)
      canvas!!.drawArc(rectRight, -60f, 45f, true, paintSide)
   }

   fun setFillMiddleWeight(weight: Float) {
      fillPercentMiddle = weight
      invalidate()
   }
}
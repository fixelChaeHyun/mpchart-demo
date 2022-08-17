package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class ArcView2 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {

   val lineColor1 = "#FDC6C6"
   val lineColor2 = "#A8BBFF"
   val lineColor3 = "#FFA833"
   val lineColor4 = "#E8DC00"
   val lineColor5 = "#F41D1D"
   val fillColorSide = "#33E3FF"

   init {

   }

   var fillPercentMiddle = 0.10f

   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)

      var paintColor1 = Paint().apply {
         strokeWidth = 9f
         color = Color.parseColor(lineColor1)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
      }

      var paintColor2 = Paint().apply {
         strokeWidth = 9f
         color = Color.parseColor(lineColor2)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
      }

      var paintColor3 = Paint().apply {
         strokeWidth = 9f
         color = Color.parseColor(lineColor3)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
      }

      var paintColor4 = Paint().apply {
         strokeWidth = 9f
         color = Color.parseColor(lineColor4)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
      }

      var paintColor5 = Paint().apply {
         strokeWidth = 9f
         color = Color.parseColor(lineColor5)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
      }


//      val fillPercentMiddle = 0.01f
      var offset = 11f
      var rectMiddle = RectF(-400f, 100f, 1500f, 3500f)
      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintColor1)
      // RectMiddle 에 Padding 을 넣을 수 있는 방법?

      // 2번 그래프
      rectMiddle = RectF(-240f, 400f, 1340f, 3200f)
      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintColor2)

      // 3번 그래프
      rectMiddle = RectF(-80f, 700f, 1180f, 2900f)
      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintColor3)

      // 4번 그래프
      rectMiddle = RectF(100f, 1000f, 1000f, 2600f)
      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintColor4)

      // 5번 그래프
      rectMiddle = RectF(280f, 1300f, 820f, 2300f)
      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintColor5)

//      val fillX = 100f * fillPercentMiddle
//      val fillY = 100f * fillPercentMiddle
//      rectMiddle.set(100f + fillX, 100 + fillY, 1000f - fillY, 1000f - fillY)
//      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintFillMiddle)





      val paintSide = Paint().apply {
         strokeWidth = 6f
         color = Color.parseColor(lineColor2)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(15f, 10f), 0f)
      }
   }
}
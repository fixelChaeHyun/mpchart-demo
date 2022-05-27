package com.example.msc_chart_demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


class ArcView constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {

   init {

   }

   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)
      val pnt = Paint()
      pnt.strokeWidth = 6f
      pnt.color = Color.parseColor("#FF0000")
      pnt.style = Paint.Style.STROKE

      var rect = RectF()
      rect[200f, 200f, 600f] = 600f
      canvas!!.drawArc(rect, 270f, 290f, false, pnt)

      rect = RectF()
      rect[300f, 700f, 700f] = 1100f
      canvas!!.drawArc(rect, 0f, 290f, true, pnt)
   }
}
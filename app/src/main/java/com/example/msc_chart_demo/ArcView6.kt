package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class ArcView6 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {
   private val TAG: String = this::class.java.simpleName

   var screenHeight: Float = 0f
   var screenWidth: Float = 0f

   var viewHeight: Float = 0f
   var viewWidth: Float = 0f


   init {
   }

   var fillPercentMiddle = 0.825f
   var useCenterLine = false


   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)
      if (canvas == null) return
//      val fillPercentMiddle = 0.01f
      var offset = 11f
      Log.e(TAG," ** screenWidth: $screenWidth, screenHeight: $screenHeight  / viewWidth: $viewWidth, viewHeight: $viewHeight **")
      Log.e(TAG, "DRAW_START > arcView.width: ${width}, arcView.height: ${height} , measuredWidth: ${measuredWidth} , measuredHeight: ${measuredHeight}")


      if (screenHeight < 0) {
         Log.e("ArcView6", "testSize is not initialized yet.")
         return
      }


      val endX = width.toFloat()
      val endY = height.toFloat()
      // 좌상단, 우하단 연결선
      canvas.drawCircle(0f, 0f, 15f, Const.teeBoxInnerStyle)
      canvas.drawCircle(endX, endY, 15f, Const.teeBoxInnerStyle)
      canvas.drawLine(0f, 0f, endX, endY, Const.guideLineBlue)
      // 우상단, 좌하단 연결선
      canvas.drawCircle(endX, 0f, 15f, Const.teeBoxInnerStyle)
      canvas.drawCircle(0f, endY, 15f, Const.teeBoxInnerStyle)
      canvas.drawLine(endX, 0f, 0f, endY, Const.guideLineBlue)

      canvas.drawLine(0f, 0f, endX, 0f, Const.guideLineBlue)
      canvas.drawLine(0f, 0f, 0f, endY, Const.guideLineBlue)
      canvas.drawLine(0f, endY, endX, endY, Const.guideLineBlue)
      canvas.drawLine(endX, 0f, endX, endY, Const.guideLineBlue)

      canvas.drawCircle(endX/2, endY/2, 20f,Const.dotBlackStyle)

      DrawUtil.drawTeeBox(canvas, endX/2, endY, 20f, 45f)

      canvas.drawLine(endX / 2, endY, 0f, 0f, Const.guideLineYellow)
      canvas.drawLine(endX / 2, endY, endX, 0f, Const.guideLineYellow)


      val startX = endX / 2
      val startY = endY.toFloat()

      val offsetValue = (width * 0.16).toFloat()

      // 비율 가이드선
      var cnt = 5
      var ratioY = startY
      while(cnt-- > 0) {
         ratioY -= offsetValue
         canvas.drawLine(0f, ratioY, endX, ratioY, Const.ballLineStyle)

      }


      Log.e(TAG, "offsetValue : $offsetValue")
      val startAng = -65f
      val sweepAng = -50f

      var left = startX - offsetValue
      var top = startY - offsetValue
      var right = startX + offsetValue
      var bottom = startY + offsetValue
      val rect1 = RectFrame(left, top, right, bottom, startAng, sweepAng)
      canvas.drawArc(RectF(rect1.left, rect1.top, rect1.right, rect1.bottom), startAng, sweepAng, useCenterLine, Const.graphArcLine2)

      left -= offsetValue
      top -= offsetValue
      right += offsetValue
      bottom += offsetValue

      var rect2 = RectFrame(left, top, right, bottom, startAng, sweepAng)
      canvas.drawArc(RectF(rect2.left, rect2.top, rect2.right, rect2.bottom), startAng, sweepAng, useCenterLine, Const.graphArcLine)

      var count = 4
      while (count > 0) {
         left -= offsetValue
         top -= offsetValue
         right += offsetValue
         bottom += offsetValue

         rect2 = RectFrame(left, top, right, bottom, startAng, sweepAng)
         canvas.drawArc(RectF(rect2.left, rect2.top, rect2.right, rect2.bottom), startAng, sweepAng, useCenterLine, Const.graphArcLine)
         count--
      }

      if (useCenterLine) {
         val sideRectFrame = RectF(left, top, right, bottom)
         canvas.drawArc(sideRectFrame, -70f, -40f, true, Const.ballColors[2])
         val centerRectFrame = RectF(left, top, right, bottom)
         canvas.drawArc(centerRectFrame, -85f, -10f, true, Const.ballColors[0])
      }


//      // ===================== Draw balls...  ===========================
//      canvas!!.drawCircle(510f, 820f, 27f, ballColors[0])
//      canvas!!.drawText("W", 498f, 829f, clubTextStyle)
//      canvas!!.drawCircle(500f, 300f, 27f, ballColors[0])
//      canvas!!.drawText("i9", 490f, 309f, clubTextStyle)
//      canvas!!.drawCircle(500f, 960f, 27f, ballColors[0])
//      canvas!!.drawText("i9", 490f, 969f, clubTextStyle)
//      canvas!!.drawCircle(486f, 860f, 27f, ballColors[0])
//      canvas!!.drawText("i9", 476f, 869f, clubTextStyle)
//
//      canvas!!.drawCircle(720f, 150f, 27f, ballColors[3])
//      canvas!!.drawText("i3", 710f, 156f, clubTextStyle)
//      canvas!!.drawCircle(572f, 450f, 27f, ballColors[3])
//      canvas!!.drawText("i3", 562f, 457f, clubTextStyle)
//
//      canvas!!.drawCircle(520f, 228f, 25f, ballColors[2])
//      canvas!!.drawText("U", 510f, 238f, clubTextStyle)
//      canvas!!.drawCircle(545f, 900f, 27f, ballColors[2])
//      canvas!!.drawText("U", 535f, 910f, clubTextStyle)
//
//      canvas!!.drawCircle(630f, 650f, 25f, ballColors[0])
//      canvas!!.drawText("W", 617f, 659f, clubTextStyle)
//
//      // 마지막 타구 라인 그리기
//      canvas!!.drawLine(550f, 1400f, 300f, 300f, ballLineStyle)
//      canvas!!.drawCircle(300f, 300f, 27f, ballColors[0])
//      canvas!!.drawText("W", 287f, 310f, clubTextStyle)

//      val fillX = 100f * fillPercentMiddle
//      val fillY = 100f * fillPercentMiddle
//      rectMiddle.set(100f + fillX, 100 + fillY, 1000f - fillY, 1000f - fillY)
//      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintFillMiddle)

//      viewWidth = this.width.toFloat()
//      viewHeight = this.height.toFloat()

      Log.e(TAG, "DRAW_END > arcView.width: ${width}, arcView.height: ${height} , measuredWidth: ${measuredWidth} , measuredHeight: ${measuredHeight}")
   }

   fun setFillMiddleWeight(weight: Float) {
      fillPercentMiddle = weight
      invalidate()
   }
}
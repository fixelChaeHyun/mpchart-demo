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

   var fillPercentMiddle: Float = 0.75f
   var validAreaForShotData = false
   var setLongClick = false


   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)
      if (canvas == null) return
//      val fillPercentMiddle = 0.01f
      var offset = 11f
      var heightRatio: Float = (height.toFloat() / width.toFloat())
      Log.e(TAG," ** screenWidth: $screenWidth, screenHeight: $screenHeight  / viewWidth: $viewWidth, viewHeight: $viewHeight **")
      Log.e(TAG, "DRAW_START > heightRatio: $heightRatio arcView.width: ${width}, arcView.height: ${height} , measuredWidth: ${measuredWidth} , measuredHeight: ${measuredHeight}")



      if (screenHeight < 0) {
         Log.e("ArcView6", "testSize is not initialized yet.")
         return
      }


      val endX = width.toFloat()
      val endY = height.toFloat()

      val startX = endX / 2
      val startY = endY.toFloat()

      val intervalArcLine = 0.165
//      heightRatio = 1.toFloat()
      val offsetValue = (width * intervalArcLine).toFloat()      // offset for Width (1배처리)
      val offsetHeight = ((width * heightRatio) * intervalArcLine).toFloat()      // offset for Height (가로 대비 세로 배율만큼 길게 offset 설정하기)

      if (setLongClick) {
         // 좌상단, 우하단 모서리
         canvas.drawCircle(0f, 0f, 15f, Const.dotBlackStyle)
         canvas.drawCircle(endX, endY, 15f, Const.dotBlackStyle)

         // 우상단, 좌하단 모서리
         canvas.drawCircle(endX, 0f, 15f, Const.dotBlackStyle)
         canvas.drawCircle(0f, endY, 15f, Const.dotBlackStyle)

         // 테두리 라인 그리기
         canvas.drawLine(0f, 0f, endX, 0f, Const.guideLineBlue)
         canvas.drawLine(0f, 0f, 0f, endY, Const.guideLineBlue)
         canvas.drawLine(0f, endY, endX, endY, Const.guideLineBlue)
         canvas.drawLine(endX, 0f, endX, endY, Const.guideLineBlue)

         // 가운데 지점 찾기
//         canvas.drawLine(0f, 0f, endX, endY, Const.guideLineBlue)
//         canvas.drawLine(endX, 0f, 0f, endY, Const.guideLineBlue)
//         canvas.drawCircle(endX/2, endY/2, 20f,Const.dotBlackStyle)

         // 사이드 가이드라인
         canvas.drawLine(endX / 2, endY, 0f, 0f, Const.guideLineYellow)
         canvas.drawLine(endX / 2, endY, endX, 0f, Const.guideLineYellow)

         var cnt = 5
         var ratioY = startY
         while(cnt-- > 0) {
            ratioY -= offsetHeight
            canvas.drawLine(0f, ratioY, endX, ratioY, Const.ballLineStyle)

         }
      }

      // Draw Tee box
      DrawUtil.drawTeeBox(canvas, endX/2, endY, 20f, 45f)


      Log.e(TAG, "offsetWidth : $offsetValue , offsetHeight: $offsetHeight , startX: $startX, startY: $startY")
      val startAng = -65f
      val sweepAng = -50f


      val distanceText = listOf("50", "100", "150", "200", "250", "")
      var left = startX - offsetValue
      var top = startY - offsetHeight
      var right = startX + offsetValue
      var bottom = startY + offsetHeight
      val count = 6
      var start = 0
      while (start < count) {

         val rect2 = RectFrame(left, top, right, bottom, startAng, sweepAng)
         if (start != count-1) {
            DrawUtil.drawArcLine2(canvas, rect2, distanceText[start])
         } else {
            DrawUtil.drawArcLine2(canvas, rect2, distanceText[start], Const.graphArcLine3)
            left += (offsetValue * 1.0).toFloat()
            top += (offsetHeight * 1.0).toFloat()
            right -= (offsetValue * 1.0).toFloat()
            bottom -= (offsetHeight * 1.0).toFloat()
            break
         }


         left -= offsetValue
         top -= offsetHeight
         right += offsetValue
         bottom += offsetHeight

         start++
      }

      // 타구 데이터 유효값 영역 그리기
      if (validAreaForShotData) {
         val sideRectFrame = RectF(left, top, right, bottom)
         canvas.drawArc(sideRectFrame, -70f, -40f, true, Const.validAreaStyle[0])
         val centerRectFrame = RectF(left, top, right, bottom)
         canvas.drawArc(centerRectFrame, -85f, -10f, true, Const.validAreaStyle[1])
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
package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.marginTop


class ArcView5 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {

   var screenHeight: Float = 0f
   var screenWidth: Float = 0f

   init {
   }

   var fillPercentMiddle = 0.825f
   var useCenterLine = false


   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)
      if (canvas == null) return
//      val fillPercentMiddle = 0.01f
      var offset = 11f
      Log.e("ArcView5","testHeight: $screenHeight , screenWidth: $screenWidth")

      if (screenHeight < 0) {
         Log.e("ArcView5", "testSize is not initialized yet.")
         return
      }



      // ScreenHeight 대신에 비율넣기
      val targetGraphHeight = screenHeight * (fillPercentMiddle * 0.788)      // 1716
      val targetGraphHeightDouble = targetGraphHeight * 2
      val targetGraphHeightTriple = targetGraphHeight * 3

      val startAngle = -60f
      val sweepAngle = -60f
      val topMargin = 100f
      val halfWidth = screenWidth / 2
      val bottomLineY = (topMargin + targetGraphHeight).toFloat()
      val topOffset = (targetGraphHeight - screenWidth).toFloat()    // Top은 offset 만큼 아래로, 좌우는 1/2만큼씩 안쪽으로 이동.
      // 좌우 1/2만큼 확장, Top은 offset만큼 위로 확대

      val newBottomLineY = (topMargin + targetGraphHeightDouble).toFloat()
      val newTopOffset = (targetGraphHeightTriple - screenWidth).toFloat()

      val gapOfHeight = (targetGraphHeight  * 0.2).toFloat()    // 5칸으로 분배
      val halfGapOfHeight = gapOfHeight / 2
//      val topGapOfHeight = (gapOfHeight * fillPercentMiddle)
//      val bottomGapOfHeight = gapOfHeight - topGapOfHeight        // TODO: 바텀 갭은 고정. 상단만 줄어드는 형태로 변경.

      Log.w("ArcView5", "halfWidth: $halfWidth , graphHeight: ${targetGraphHeight}, "+
              "\ngapOfHeight: $gapOfHeight, halfGapHeight: $halfGapOfHeight")

      val leftInset = (topOffset) / 2  // 이게 1번 Arc의 left 값
      val newLeftInset = (newTopOffset) / 2

      /**  부채꼴이 좌우 대칭으로 그려지려면  Rect 는 항상 정사각형이어야한다.*/
      // TODO: 그래프 한번에 5개 비율맞춰서 그려주도록 함수 생성.
      val distanceList = listOf("250", "200", "150", "100", "50")

      var left = -leftInset
      var right = (targetGraphHeight - leftInset).toFloat()
      var top = topMargin
      var bottom = bottomLineY
      Log.w("ArcView5", " -> left: $left, right: $right, top: $top, bottom: $bottom")

      // Center Area

      if (useCenterLine) {
         val sideRectFrame = RectF(left, 100f, right, bottom*2)
         canvas.drawArc(sideRectFrame, -70f, -40f, true, Const.ballColors[2])
         val centerRectFrame = RectF(left, 100f, right, bottom * 2)
         canvas.drawArc(centerRectFrame, -85f, -10f, true, Const.ballColors[0])
      }

      bottom = bottom * 2
//      val rectFrame1 = RectFrame(left, topMargin, screenWidth + (topOffset/2), bottomLineY, -60f, -60f, false)
      Log.e("ArcView5", "[1] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame1 = RectFrame(left, top, right, bottom, -60f, -60f, useCenterLine)
      DrawUtil.drawArcLine(canvas, rectFrame1, distanceList[0])

      // ===========================================================

      if (useCenterLine) {
         var newLeft = -newLeftInset
         var newRight = (targetGraphHeightDouble - leftInset).toFloat()
         var newTop = topMargin
         var newBottom = newBottomLineY
         val testRectFrame1 = RectFrame(newLeft, newTop, newRight, newBottom, -80f, -20f, useCenterLine)
         DrawUtil.drawArcLine(canvas, testRectFrame1, "1번", Const.graphArcLine2)

         newLeft = newLeft + gapOfHeight
         newRight = newRight - gapOfHeight

         newTop = newTop + gapOfHeight
         newBottom = newBottom - gapOfHeight
         val testRectFrame2 = RectFrame(newLeft, newTop, newRight, newBottom, -80f, -20f, useCenterLine)
         DrawUtil.drawArcLine(canvas, testRectFrame2, "2번", Const.graphArcLine2)
      }

      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom - gapOfHeight
      // 2번 그래프 - orange
      Log.e("ArcView5", "[2] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame2 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      DrawUtil.drawArcLine(canvas, rectFrame2, distanceList[1])


      // ===========================================================
      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom - gapOfHeight
      // 3번 그래프 - orange
      Log.e("ArcView5", "[3] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame3 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      DrawUtil.drawArcLine(canvas, rectFrame3, distanceList[2])



      // ===========================================================
      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom - gapOfHeight
      // 4번 그래프 - oragne
      Log.e("ArcView5", "[4] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame4 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      DrawUtil.drawArcLine(canvas, rectFrame4, distanceList[3])



      // ===========================================================
      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom - gapOfHeight
      // 5번 그래프 - orange
      Log.e("ArcView5", "[5] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame5 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      DrawUtil.drawArcLine(canvas, rectFrame5, distanceList[4])



      // 티박스 그리기 원형 2개
      val teeBoxCenterX = halfWidth
      val teeBoxCenterY = bottomLineY        // TODO: 마지막 5번째 호 그래프의 Top 에서 + 간격(300)만큼
      Log.w("ArcView5", "teeBox_x: $teeBoxCenterX, teeBox_y: $teeBoxCenterY")
      DrawUtil.drawTeeBox(canvas, teeBoxCenterX, teeBoxCenterY, 35f * (fillPercentMiddle), 100f * (fillPercentMiddle))


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
   }

   fun setFillMiddleWeight(weight: Float) {
      fillPercentMiddle = weight
      invalidate()
   }
}
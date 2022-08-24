package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.cos
import kotlin.math.sin


class ArcView7 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {
   private val TAG: String = this::class.java.simpleName

   var screenHeight: Float = 0f
   var screenWidth: Float = 0f

   var viewHeight: Float = 0f
   var viewWidth: Float = 0f

   var ratioDistance = 0f
   var ratioRadius = 0f
   var ratioDistanceWidth = 0f
   var ratioRadiusWidth = 0f

   private val shotData : ArrayList<BallShotData> = arrayListOf()

   init {
   }

   var fillPercentMiddle: Float = 1.0f
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

      val widthAdjusted = width * fillPercentMiddle
      val heightAdjusted = height * fillPercentMiddle

      val minusWidth = width.toFloat() - widthAdjusted
      val minusHeight = height.toFloat() - heightAdjusted


      if (screenHeight < 0) {
         Log.e("ArcView6", "testSize is not initialized yet.")
         return
      }

//      val initialWidth = width.toFloat()
//      val initialHeight = height.toFloat()

      val leftTopX = 0f + minusWidth/2
      val leftTopY = 0f + minusHeight/2

//      val rightTopX = 0f - minusWidth/2
//      val rightTopY = 0f - minusHeight/2

      val endX = width.toFloat() - minusWidth/2
      val endY = height.toFloat() - minusHeight/2
      val startX = width.toFloat() / 2
      val startY = endY

      Log.d(TAG, "Adjusted >  adWidth: $widthAdjusted, adHeight: $heightAdjusted ,  minusWidth: $minusWidth , minusHeight: $minusHeight")

      val intervalArcLine = 0.165
//      heightRatio = 1.toFloat()
      val offsetValue = (widthAdjusted * intervalArcLine).toFloat()      // offset for Width (1배처리)
      val offsetHeight = ((widthAdjusted * heightRatio) * intervalArcLine).toFloat()      // offset for Height (가로 대비 세로 배율만큼 길게 offset 설정하기)

      if (setLongClick) {
         // 좌상단, 우하단 모서리
         canvas.drawCircle(leftTopX, leftTopY, 15f, Const.dotBlackStyle)
         canvas.drawCircle(endX, endY, 15f, Const.dotBlackStyle)

         // 우상단, 좌하단 모서리
         canvas.drawCircle(endX, leftTopY, 15f, Const.dotBlackStyle)
         canvas.drawCircle(leftTopX, endY, 15f, Const.dotBlackStyle)

         // 테두리 라인 그리기
         canvas.drawLine(leftTopX, leftTopY, endX, leftTopY, Const.guideLineBlue)  // 상단 가로선
         canvas.drawLine(leftTopX, leftTopY, leftTopX, endY, Const.guideLineBlue)   // 좌측 세로선
         canvas.drawLine(leftTopX, endY, endX, endY, Const.guideLineBlue)     // 하단 가로선
         canvas.drawLine(endX, leftTopY, endX, endY, Const.guideLineBlue)     // 우측 세로선

         // 가운데 지점 찾기
         canvas.drawLine(leftTopX, leftTopY, endX, endY, Const.guideLineBlue)
         canvas.drawLine(endX, leftTopY, leftTopX, endY, Const.guideLineBlue)
         canvas.drawCircle((widthAdjusted+minusWidth)/2, (heightAdjusted+minusHeight)/2, 10f,Const.dotBlackStyle)

         // 사이드 가이드라인 - endX + minusWidth/2 = originalWidth
         canvas.drawLine(width.toFloat() / 2, endY, leftTopX, leftTopY, Const.guideLineYellow)
         canvas.drawLine(width.toFloat() / 2, endY, endX, leftTopY, Const.guideLineYellow)

         var cnt = 5
         var ratioY = startY
         while(cnt-- > 0) {
            ratioY -= offsetHeight
            canvas.drawLine(leftTopX, ratioY, endX, ratioY, Const.ballLineStyle)

         }
      }

      // Draw Tee box
      DrawUtil.drawTeeBox(canvas, (endX + minusWidth/2)/2 , endY, 25f*fillPercentMiddle, 55f*fillPercentMiddle)


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

      var radiusThird = 0f
      var radiusThirdWidth = 0f
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

         if (start == 2) {
            radiusThird = (bottom - top) / 2
            radiusThirdWidth = (right - left) / 2
            ratioDistance = radiusThird
            ratioRadius = 150f
            ratioDistanceWidth = radiusThirdWidth
            ratioRadiusWidth = 150f
            Log.e("ArcView6", "!! Check Radius[${distanceText[start]}] = $radiusThird")
            Log.e("ArcView6", "!! Center dot: (${(widthAdjusted+minusWidth)/2}, ${heightAdjusted+minusHeight/2})")
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


      shotData.forEachIndexed { index, ballShotData ->
         val lineDraw = if (shotData.size == 1) true else index == shotData.size -1

         drawShotData(canvas, ballShotData.distance, ballShotData.rad, radiusThirdWidth, radiusThird, 150f, ballShotData.paint, lineDraw)
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

   private fun drawShotData(canvas: Canvas, targetDistance: Float, targetRad: Double, ratioRadiusWidth: Float, ratioRadius: Float, ratioDistance: Float, paint: Paint = Const.ballColors[1], drawLine: Boolean = false) {
      if (ratioDistance != 0f && ratioRadius != 0f && ratioDistance != 0f) {
         val minusHeight = height.toFloat() - (height * fillPercentMiddle)
         val endY = height.toFloat() - minusHeight/2
         val rad = targetRad - 90

         var result = ratioRadius * targetDistance / ratioDistance
         var resultWidth = ratioRadiusWidth * targetDistance / ratioDistance

         // result 값이 x,y축 같은 값만큼 줄어들어서 맞지 않는 상태 -> 비율 x축 따로 구해야함.
         val x = cos(Math.toRadians(rad)) * resultWidth
         val y = sin(Math.toRadians(rad)) * result
         Log.e("ArcView6", "!!! x,y 좌표 -> ($x, $y) / (${width.toFloat()/2 + x}, ${endY + y.toFloat()})")

         if (drawLine) {
            canvas.drawLine(width.toFloat() / 2, endY, x.toFloat() + width.toFloat()/2, y.toFloat() + endY, Const.ballLineStyle)
         }
         canvas.drawCircle(x.toFloat() + width.toFloat()/2, y.toFloat() + endY, 15f, paint)
      }
   }

   fun setFillMiddleWeight(weight: Float) {
      fillPercentMiddle = weight
      invalidate()
   }

   fun setShotData(dummies: List<BallShotData>) {
      shotData.clear()
      shotData.addAll(dummies)
      invalidate()
   }
}
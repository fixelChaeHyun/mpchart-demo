package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.marginTop


class ArcView4 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {

   var fillPercentMiddle = 0.825f
   var useCenterLine = false
   var setOutline = false

   data class RectFrame(val left: Float, val top: Float, val right: Float, val bottom: Float, val startAngle: Float, val sweepAngle: Float, val useCenter: Boolean = false)

   var screenHeight: Float = 0f
   var screenWidth: Float = 0f

   private val colorWhite = "#FFFFFF"      // White
   private val colorPurple = "#A8BBFF"
   private val colorBlue = "#2554FD"
   private val colorYellow = "#E8DC00"
   private val colorOrange = "#FF6702"
   private val yellowColor = "#FFD500"
   private val greenColor = "#429F4E"

   private val textSizeForDistance: Float = 32f
   private val textSizeForClub: Float = 28f

   private val ballColors = listOf<Paint>(
      Paint().apply {
         color = Color.parseColor(colorPurple)
         style = Paint.Style.FILL
      },
      Paint().apply {
         color = Color.parseColor(colorBlue)
         style = Paint.Style.FILL
      },
      Paint().apply {
         color = Color.parseColor(colorYellow)
         style = Paint.Style.FILL
      },
      Paint().apply {
         color = Color.parseColor(colorOrange)
         style = Paint.Style.FILL
      }
   )

   val distanceTextStyle = Paint().apply {
      strokeWidth = 0.5f
      color = Color.parseColor(colorWhite)
      style = Paint.Style.FILL_AND_STROKE
      textSize = textSizeForDistance
   }

   val distanceTextStyle2 = Paint().apply {
      strokeWidth = 0.5f
      color = Color.parseColor(colorBlue)
      style = Paint.Style.FILL_AND_STROKE
      textSize = textSizeForDistance
   }

   val clubTextStyle = Paint().apply {
      strokeWidth = 0.5f
      color = Color.parseColor(colorWhite)
      style = Paint.Style.FILL_AND_STROKE
      textSize = textSizeForClub
   }

   val centerLineStyle = Paint().apply {
      strokeWidth = 3f
      color = Color.parseColor(colorWhite)
      style = Paint.Style.STROKE
      pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
   }

   var ballLineStyle = Paint().apply {
      strokeWidth = 3f
      color = Color.parseColor(yellowColor)
      style = Paint.Style.STROKE
      pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
   }

   val teeBoxStyle = Paint().apply {
      color = Color.parseColor(greenColor)
      style = Paint.Style.FILL_AND_STROKE
   }

   val teeBoxSmallStyle = Paint().apply {
      color = Color.parseColor(colorOrange)
      style = Paint.Style.FILL_AND_STROKE
   }

   val graphArcLine = Paint().apply {
      strokeWidth = 3f
      color = Color.parseColor(colorWhite)
      style = Paint.Style.STROKE
   }

   val graphArcLine2 = Paint().apply {
      strokeWidth = 5f
      color = Color.parseColor(colorOrange)
      style = Paint.Style.STROKE
   }

   init {
   }


   private fun drawTeeBox(canvas: Canvas, centerX: Float, centerY: Float, smallRadius: Float, largeRadius: Float) {
      canvas.drawCircle(centerX, centerY, largeRadius, teeBoxStyle)
      canvas.drawCircle(centerX, centerY, smallRadius, teeBoxSmallStyle)
      canvas.drawLine(centerX, 0f, centerX, centerY, centerLineStyle)
   }

   private fun drawArcLine(canvas: Canvas, rect: RectFrame, distanceText: String, paintOption: Paint = graphArcLine) {
      val rectFrame = RectF(rect.left, rect.top, rect.right, rect.bottom)
      canvas.drawArc(rectFrame, rect.startAngle, rect.sweepAngle, rect.useCenter, graphArcLine)

      // 추가 위치 계산 필요.
      val width = rect.right - rect.left
      val height = rect.bottom - rect.top
      val textLeft: Float = ((rect.left*0.8) + (width * 0.17)).toFloat()
      val textTop: Float = (rect.top + height * 0.07).toFloat()
      canvas.drawText(distanceText, textLeft, textTop, distanceTextStyle)
   }

   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)
      if (canvas == null) return
//      val fillPercentMiddle = 0.01f
      var offset = 11f
      Log.e("ArcView4","testHeight: $screenHeight , screenWidth: $screenWidth")

      if (screenHeight < 0) {
         Log.e("ArcView4", "testSize is not initialized yet.")
         return
      }



      // ScreenHeight 대신에 비율넣기
      val targetGraphHeight = screenHeight * (fillPercentMiddle * 0.788)      // 1716
      val targetGraphHeightDouble = targetGraphHeight * 2

      val startAngle = -60f
      val sweepAngle = -60f
      val topMargin = 100f
      val halfWidth = screenWidth / 2
      val bottomLineY = (topMargin + targetGraphHeight).toFloat()
      val topOffset = (targetGraphHeight - screenWidth).toFloat()    // Top은 offset 만큼 아래로, 좌우는 1/2만큼씩 안쪽으로 이동.
      // 좌우 1/2만큼 확장, Top은 offset만큼 위로 확대




      val gapOfHeight = (targetGraphHeight * 0.2).toFloat()    // 5칸으로 분배
      val halfGapOfHeight = gapOfHeight / 2
//      val topGapOfHeight = (gapOfHeight * fillPercentMiddle)
//      val bottomGapOfHeight = gapOfHeight - topGapOfHeight        // TODO: 바텀 갭은 고정. 상단만 줄어드는 형태로 변경.

      Log.w("ArcView4", "halfWidth: $halfWidth , graphHeight: ${targetGraphHeight}, "+
              "\ngapOfHeight: $gapOfHeight, halfGapHeight: $halfGapOfHeight")

      val leftInset = (topOffset) / 2  // 이게 1번 Arc의 left 값


      /**  부채꼴이 좌우 대칭으로 그려지려면  Rect 는 항상 정사각형이어야한다.*/
      // TODO: 그래프 한번에 5개 비율맞춰서 그려주도록 함수 생성.
      val distanceList = listOf("250", "200", "150", "100", "50")

      var left = -leftInset
      var right = (targetGraphHeight - leftInset).toFloat()
      var top = topMargin
      var bottom = bottomLineY
      Log.w("ArcView4", " -> left: $left, right: $right, top: $top, bottom: $bottom")

      // Center Area

      if (useCenterLine) {
         val sideRectFrame = RectF(left, 100f, right, bottom*2)
         canvas.drawArc(sideRectFrame, -70f, -40f, true, ballColors[2])
         val centerRectFrame = RectF(left, 100f, right, bottom * 2)
         canvas.drawArc(centerRectFrame, -85f, -10f, true, ballColors[0])
      }

      if (setOutline) {
         val endX = halfWidth
         val endY = bottomLineY
         val textLeft: Float = ((left) + (width * 0.27)).toFloat()
         val textRight: Float = (right - (width * 0.27)).toFloat()
         val textTop: Float = (top + height * 0.044).toFloat()
         canvas.drawLine(textLeft, textTop, endX, endY, graphArcLine)

         canvas.drawLine(textRight, textTop, endX, endY, graphArcLine)
      }

//      bottom = bottom * 2
//      val rectFrame1 = RectFrame(left, topMargin, screenWidth + (topOffset/2), bottomLineY, -60f, -60f, false)
      Log.e("ArcView4", "[1] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame1 = RectFrame(left, top, right, bottom, -60f, -60f, useCenterLine)
      drawArcLine(canvas, rectFrame1, distanceList[0])




      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom
      // 2번 그래프 - orange
      Log.e("ArcView4", "[2] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame2 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      drawArcLine(canvas, rectFrame2, distanceList[1])


      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom
      // 3번 그래프 - orange
      Log.e("ArcView4", "[3] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame3 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      drawArcLine(canvas, rectFrame3, distanceList[2])

      if (setOutline) {
         val sideRectFrame = RectF(left, top, right, (bottom-gapOfHeight) * 2)
         canvas.drawArc(sideRectFrame, -70f, -40f, true, ballColors[3])
      }



      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom
      // 4번 그래프 - oragne
      Log.e("ArcView4", "[4] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame4 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      drawArcLine(canvas, rectFrame4, distanceList[3])



      left = left + halfGapOfHeight
      right = right - halfGapOfHeight
      top = top + gapOfHeight
      bottom = bottom
      // 5번 그래프 - orange
      Log.e("ArcView4", "[5] left: $left, top: $top, right: $right, bottom: $bottom")
      val rectFrame5 = RectFrame(left, top, right, bottom, startAngle, sweepAngle, useCenterLine)
      drawArcLine(canvas, rectFrame5, distanceList[4])



      // 티박스 그리기 원형 2개
      val teeBoxCenterX = halfWidth
      val teeBoxCenterY = bottomLineY        // TODO: 마지막 5번째 호 그래프의 Top 에서 + 간격(300)만큼
      Log.w("ArcView4", "teeBox_x: $teeBoxCenterX, teeBox_y: $teeBoxCenterY")
      drawTeeBox(canvas, teeBoxCenterX, teeBoxCenterY, 35f * (fillPercentMiddle), 100f * (fillPercentMiddle))


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
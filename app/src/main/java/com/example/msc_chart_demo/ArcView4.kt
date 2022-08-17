package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


class ArcView4 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {

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

   var fillPercentMiddle = 0.10f

   private fun drawTeeBox(canvas: Canvas, centerX: Float, centerY: Float, smallRadius: Float, largeRadius: Float) {
      canvas.drawCircle(centerX, centerY, largeRadius, teeBoxStyle)
      canvas.drawCircle(centerX, centerY, smallRadius, teeBoxSmallStyle)
      canvas.drawLine(centerX, 0f, centerX, centerY, centerLineStyle)
   }

   private fun drawArcLine(canvas: Canvas, rect: RectFrame, distanceText: String) {
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

      val calcScreenHeight = screenHeight * 0.65      // 1716

      val halfWidth = screenWidth / 2
      val gapOfHeight = (calcScreenHeight * 0.18).toFloat()

      Log.w("ArcView4", "halfWidth: $halfWidth , graphHeight: ${calcScreenHeight}, gapHeight: $gapOfHeight")

      val leftInset = (calcScreenHeight - screenWidth) / 2  // 이게 1번 Arc의 left 값

      // 티박스 그리기 원형 2개
      val teeBoxCenterX = halfWidth
      val teeBoxCenterY = 1400f        // TODO: 마지막 5번째 호 그래프의 Top 에서 + 간격(300)만큼
      drawTeeBox(canvas, teeBoxCenterX, teeBoxCenterY, 35f, 90f)

      /**  부채꼴이 좌우 대칭으로 그려지려면  Rect 는 항상 정사각형이어야한다.*/
      // TODO: 그래프 한번에 5개 비율맞춰서 그려주도록 함수 생성.
      val distanceList = listOf("250", "200", "150", "100", "50")
      var left = leftInset
      var right = calcScreenHeight - leftInset
      val rectFrame1 = RectFrame(-300f, 100f, 1400f, 1800f, -60f, -60F)
      drawArcLine(canvas, rectFrame1, distanceList[0])
      // 2번 그래프 - orange
      val rectFrame2 = RectFrame(-150f, 350f, 1250f, 1750f, -60f, -60f)
      drawArcLine(canvas, rectFrame2, distanceList[1])
      // 3번 그래프 - orange
      val rectFrame3 = RectFrame(0f, 600f, 1100f, 1700f, -60f, -60f)
      drawArcLine(canvas, rectFrame3, distanceList[2])
      // 4번 그래프 - oragne
      val rectFrame4 = RectFrame(130f, 850f, 950f, 1650f, -60f, -60f)
      drawArcLine(canvas, rectFrame4, distanceList[3])
      // 5번 그래프 - orange
      val rectFrame5 = RectFrame(300f, 1100f, 800f, 1600f, -60f, -60f)
      drawArcLine(canvas, rectFrame5, distanceList[4])



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
}
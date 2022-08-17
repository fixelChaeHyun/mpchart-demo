package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class ArcView3 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {

   private val colorWhite = "#FFFFFF"      // White
   private val colorPurple = "#A8BBFF"
   private val colorBlue = "#2554FD"
   private val colorYellow = "#E8DC00"
   private val colorOrange = "#FF6702"
   private val yellowColor = "#FFD500"
   private val greenColor = "#429F4E"

   private val textSizeForDistance: Float = 32f
   private val textSizeForClub: Float = 28f

   val ballColors = listOf<Paint>(
      Paint().apply {
         strokeWidth = 3f
         color = Color.parseColor(colorPurple)
         style = Paint.Style.FILL
      },
      Paint().apply {
         strokeWidth = 3f
         color = Color.parseColor(colorBlue)
         style = Paint.Style.FILL
      },
      Paint().apply {
         strokeWidth = 3f
         color = Color.parseColor(colorYellow)
         style = Paint.Style.FILL
      },
      Paint().apply {
         strokeWidth = 3f
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

   init {

   }

   var fillPercentMiddle = 0.10f

   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)

      var textColor1 = Paint().apply {
         strokeWidth = 0.5f
         color = Color.parseColor(colorWhite)
         style = Paint.Style.FILL_AND_STROKE
         textSize = 32f
      }

      var paintColor6 = Paint().apply {
         strokeWidth = 3f
         color = Color.parseColor(colorWhite)
         style = Paint.Style.STROKE
         pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 0f)
      }







//      val fillPercentMiddle = 0.01f
      var offset = 11f

      // 티박스 그리기 원형 2개
      canvas!!.drawCircle(550f, 1400f, 100f, teeBoxStyle)
      canvas!!.drawCircle(550f, 1400f, 35f, teeBoxSmallStyle)
      canvas!!.drawLine(550f, 0f, 550f, 1400f, centerLineStyle)

      // TODO: 그래프 한번에 5개 비율맞춰서 그려주도록 함수 생성.
      // 1번 그래프
      var rectMiddle = RectF(-300f, 100f, 1400f, 1800f)
      canvas!!.drawArc(rectMiddle, -60f, -60f, false, graphArcLine)
      canvas!!.drawText("250", 30f, 250f, textColor1)

      // 2번 그래프
      rectMiddle = RectF(-150f, 350f, 1250f, 1950f)
      canvas!!.drawArc(rectMiddle, -60f, -60f, false, graphArcLine)
      canvas!!.drawText("200", 95f, 480f, textColor1)

      // 3번 그래프
      rectMiddle = RectF(0f, 600f, 1100f, 2150f)
      canvas!!.drawArc(rectMiddle, -60f, -60f, false, graphArcLine)
      canvas!!.drawText("150", 170f, 720f, textColor1)

      // 4번 그래프
      rectMiddle = RectF(130f, 850f, 970f, 2200f)
      canvas!!.drawArc(rectMiddle, -60f, -60f, false, graphArcLine)
      canvas!!.drawText("100", 235f, 970f, textColor1)

      // 5번 그래프
      rectMiddle = RectF(250f, 1100f, 850f, 2000f)
      canvas!!.drawArc(rectMiddle, -60f, -60f, false, graphArcLine)
      canvas!!.drawText("50", 295f, 1185f, textColor1)




      // ===================== Draw balls...  ===========================
      canvas!!.drawCircle(510f, 820f, 27f, ballColors[0])
      canvas!!.drawText("W", 498f, 829f, clubTextStyle)
      canvas!!.drawCircle(500f, 300f, 27f, ballColors[0])
      canvas!!.drawText("i9", 490f, 309f, clubTextStyle)
      canvas!!.drawCircle(500f, 960f, 27f, ballColors[0])
      canvas!!.drawText("i9", 490f, 969f, clubTextStyle)
      canvas!!.drawCircle(486f, 860f, 27f, ballColors[0])
      canvas!!.drawText("i9", 476f, 869f, clubTextStyle)

      canvas!!.drawCircle(720f, 150f, 27f, ballColors[3])
      canvas!!.drawText("i3", 710f, 156f, clubTextStyle)
      canvas!!.drawCircle(572f, 450f, 27f, ballColors[3])
      canvas!!.drawText("i3", 562f, 457f, clubTextStyle)

      canvas!!.drawCircle(520f, 228f, 25f, ballColors[2])
      canvas!!.drawText("U", 510f, 238f, clubTextStyle)
      canvas!!.drawCircle(545f, 900f, 27f, ballColors[2])
      canvas!!.drawText("U", 535f, 910f, clubTextStyle)

      canvas!!.drawCircle(630f, 650f, 25f, ballColors[0])
      canvas!!.drawText("W", 617f, 659f, clubTextStyle)

      // 마지막 타구 라인 그리기
      canvas!!.drawLine(550f, 1400f, 300f, 300f, ballLineStyle)
      canvas!!.drawCircle(300f, 300f, 27f, ballColors[0])
      canvas!!.drawText("W", 287f, 310f, clubTextStyle)

//      val fillX = 100f * fillPercentMiddle
//      val fillY = 100f * fillPercentMiddle
//      rectMiddle.set(100f + fillX, 100 + fillY, 1000f - fillY, 1000f - fillY)
//      canvas!!.drawArc(rectMiddle, 240f, 60f, true, paintFillMiddle)
   }
}
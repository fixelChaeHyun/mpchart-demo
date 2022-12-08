package com.example.msc_chart_demo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlin.math.cos
import kotlin.math.sin


class ArcView6 constructor(context: Context, attrs: AttributeSet) : View(context, attrs) {
   private val TAG: String = this::class.java.simpleName

   var screenHeight: Float = 0f
   var screenWidth: Float = 0f

   var viewHeight: Float = 0f
   var viewWidth: Float = 0f

   var ratioDistance = 0f
   var ratioRadius = 0f

   private val shotData : ArrayList<BallShotData> = arrayListOf()


   private val ballCustomView = BallCustomView(context = this.context, null)
   var container: LinearLayout = LinearLayout(this.context)

   init {
      container.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
      container.orientation = LinearLayout.VERTICAL
      container.setBackgroundColor(resources.getColor(R.color.greyTrans))

      var ballSize = 70

      ballCustomView.text =  ClubTypeColorData("W3", Color.argb(255, 37, 84, 253))
      ballCustomView.scale = 0.08f
      ballCustomView.layoutParams = LinearLayout.LayoutParams(ballSize, ballSize)
      ballCustomView.setOnClickListener {
         Toast.makeText(this.context, "ClubText:${ballCustomView.text.clubTypeText}", Toast.LENGTH_SHORT).show()
      }

      container.addView(ballCustomView)
      container.visibility = View.GONE
   }

   var fillPercentMiddle: Float = 1.0f
   var validAreaForShotData = false
   var setLongClick = false
   var showLayer = false


   var offsetHeight: Float = 0f
   var offsetValue: Float = 0f
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
      offsetValue = (widthAdjusted * intervalArcLine).toFloat()      // offset for Width (1배처리)
      offsetHeight = ((widthAdjusted * heightRatio) * intervalArcLine).toFloat()      // offset for Height (가로 대비 세로 배율만큼 길게 offset 설정하기)

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

         /** 가로 비율 선 */
         var cnt = 5
         var ratioY = startY
         while(cnt-- > 0) {
            ratioY -= offsetHeight
            canvas.drawLine(leftTopX, ratioY, endX, ratioY, Const.ballLineStyle)

         }
         /** 세로 비율선 */
         // 중앙선
         canvas.drawLine(width.toFloat()/2, endY, width.toFloat()/2, leftTopX, Const.ballLineStyle)
         var offset = offsetValue
         var lineStartX = width.toFloat()/2 - offset
         canvas.drawLine(lineStartX, endY, lineStartX, leftTopX, Const.ballLineStyle)
         lineStartX = width.toFloat()/2 + offset
         canvas.drawLine(lineStartX, endY, lineStartX, leftTopX, Const.ballLineStyle)

         lineStartX = width.toFloat()/2 + offset*2
         canvas.drawLine(lineStartX, endY, lineStartX, leftTopX, Const.ballLineStyle)
         lineStartX = width.toFloat()/2 - offset*2
         canvas.drawLine(lineStartX, endY, lineStartX, leftTopX, Const.ballLineStyle)

         lineStartX = width.toFloat()/2 + offset*3
         canvas.drawLine(lineStartX, endY, lineStartX, leftTopX, Const.ballLineStyle)
         lineStartX = width.toFloat()/2 - offset*3
         canvas.drawLine(lineStartX, endY, lineStartX, leftTopX, Const.ballLineStyle)

      }

      // Draw Tee box
      DrawUtil.drawTeeBox(canvas, (endX + minusWidth/2)/2 , endY, 20f*fillPercentMiddle, 45f*fillPercentMiddle)


      Log.e(TAG, "offsetWidth : $offsetValue , offsetHeight: $offsetHeight , startX: $startX, startY: $startY")
      val startPoint = 65f
      val endPoint = 115f

      val startAng = 360f - endPoint    // 245
      val sweepAng = endPoint - startPoint


      val distanceText = listOf("50", "100", "150", "200", "250", "300")
      var left = startX - offsetValue
      var top = startY - offsetHeight
      var right = startX + offsetValue
      var bottom = startY + offsetHeight
      val count = 6
      var start = 0

      var radiusThird: Float = 0f
      while (start < count) {

         val rect2 = RectFrame(left, top, right, bottom, startAng, sweepAng)
         if (start != count-1) {
            DrawUtil.drawArcLine2(canvas, rect2, distanceText[start])
         } else {
            DrawUtil.drawArcLine2(canvas, rect2, distanceText[start], Const.graphArcLine)
            left += (offsetValue * 1.0).toFloat()
            top += (offsetHeight * 1.0).toFloat()
            right -= (offsetValue * 1.0).toFloat()
            bottom -= (offsetHeight * 1.0).toFloat()
            break
         }

         Log.i("ArcView6", "!!! Radius[$start]: ${(bottom-top)/2}")

         if (start == 2) {
            radiusThird = (bottom - top) / 2
            ratioDistance = radiusThird
            ratioRadius = 150f
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

      // 타구 안착지점 그리기 테스트
//      if (radiusThird != 0f) {
//         val rad = -110.0
//
//         var targetDistance = 110f
//         var result = radiusThird * targetDistance / 150
//
//         val x = cos(Math.toRadians(rad)) * result
//         val y = sin(Math.toRadians(rad)) * result
//         Log.e("ArcView6", "!!! x,y 좌표 -> ($x, $y) / (${width.toFloat()/2 + x}, ${endY + y.toFloat()})")
////         width.toFloat() / 2, endY
//         canvas.drawCircle(x.toFloat(), y.toFloat(), 15f, Const.ballColors[2])
////         canvas.drawLine(width.toFloat() / 2, endY, x.toFloat(), y.toFloat(), Const.ballLineStyle)
//         canvas.drawCircle(x.toFloat() + width.toFloat()/2, y.toFloat() + endY, 15f, Const.ballColors[1])
//         canvas.drawLine(width.toFloat() / 2, endY, x.toFloat() + width.toFloat()/2, y.toFloat() + endY, Const.ballLineStyle)
//      }
      /*
      drawShotData(canvas, 110f, -20.0, radiusThird, 150f)
      drawShotData(canvas, 100f, 0.0, radiusThird, 150f, Const.ballColors[3])
      */

      /** 샷데이터 리스트 그리기 */
      shotData.forEachIndexed { index, ballShotData ->
         val lineDraw = if (shotData.size == 1) true else index == shotData.size -1

         drawShotData(canvas, ballShotData.distance, ballShotData.rad, radiusThird, 150f, ballShotData.paint, ballShotData.clubType, lineDraw)
      }

      Log.e(TAG, "DRAW_END > arcView.width: ${width}, arcView.height: ${height} , measuredWidth: ${measuredWidth} , measuredHeight: ${measuredHeight}")
      Log.e(TAG," ContainerSize > width: ${container.width} , height: ${container.height}")

      if (showLayer) {
         container.visibility = VISIBLE
         container.x = this.x
         container.y = this.y

         ballCustomView.x = container.layoutParams.width / 2f
         ballCustomView.y = container.layoutParams.height / 2f
         ballCustomView.invalidate()
         container.requestLayout()
      } else {
         container.visibility = GONE
      }

   }

   private fun drawShotData(canvas: Canvas, targetDistance: Float, targetRad: Double, ratioRadius: Float, ratioDistance: Float, paint: Paint = Const.ballColors[1], clubType: String, drawLine: Boolean = false) {
      if (ratioDistance != 0f && ratioRadius != 0f && ratioDistance != 0f) {
         val minusHeight = height.toFloat() - (height * fillPercentMiddle)
         val endY = height.toFloat() - minusHeight/2
         val rad = targetRad - 90

         var result = ratioRadius * targetDistance / ratioDistance

         val x = cos(Math.toRadians(rad)) * result
         val y = sin(Math.toRadians(rad)) * result
         Log.e("ArcView6", "!!! x,y 좌표 -> ($x, $y) / teeBox 추가거리: (${width.toFloat()/2 + x}, ${endY + y.toFloat()})")

         /** Draw Line - Add Animation drawing lines by time. */
         if (drawLine) {
            canvas.drawLine(width.toFloat() / 2, endY, x.toFloat() + width.toFloat()/2, y.toFloat() + endY, Const.ballLineStyle)
         }



         val ballX = x.toFloat() + width.toFloat()/2
         val ballY = y.toFloat() + endY



         Toast.makeText(context, "BallX: ${ballX-492} , BallY: ${984-ballY}\noffsetWidth: $offsetValue , offsetHeight: $offsetHeight /\n calcX: ${(492-ballX) / 50 / offsetValue} , calcY: ${(984-ballY) / 50 / offsetHeight}", Toast.LENGTH_SHORT).show()
         Log.e("좌표", "BallX: ${ballX-492} , BallY: ${984-ballY}\noffsetWidth: $offsetValue , offsetHeight: $offsetHeight /\n calcX: ${(492-ballX) / 50 / offsetValue} , calcY: ${(984-ballY) / 50 / offsetHeight}")
         Log.d("좌표", "티박스에서 거리BallX: ${492-ballX} , BallY: ${984-ballY}\noffsetWidth: $offsetValue , offsetHeight: $offsetHeight /\n calcX: ${x / 50 / offsetValue} , calcY: ${y / 50 / offsetHeight}")
         Log.d("좌표", "!!! x,y 좌표 -> ($x, $y) / teeBox 추가거리: (${width.toFloat()/2 + x}, ${endY + y.toFloat()}) / teeBox 좌표(${width.toFloat()/2}, ${endY})" )
         canvas.drawCircle(ballX, ballY, 30f, paint)



         var textX = ballX
         var textY = ballY
         when (clubType) {
            "I9", "I6", "I3" -> {
               textX += -10
               textY += 8
            }
            "U3" -> {
               textX += -14
               textY += 8
            }
            "PW", "GW" -> {
               textX += -18
               textY += 9
            }
         }
         canvas.drawText(clubType, textX, textY, Const.clubTextStyle)
      }
   }

   // TODO: Activity 에서 ArcView 사이즈를 강제로 변경시킬 경우 값 확인해보기
   override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
      super.onSizeChanged(w, h, oldw, oldh)
      Log.i("ArcView6", "w: $w, h: $h, oldW: $oldw, oldH: $oldh")
   }

   override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
      Log.i("ArcView6", "* widthMeasureSpec: $widthMeasureSpec, heightMeasureSpec: $heightMeasureSpec")

      val minw = paddingLeft + paddingRight + suggestedMinimumWidth
      val w = View.resolveSizeAndState(minw, widthMeasureSpec, 1)

      // Whatever the width ends up being, ask for a height that would let the pie get as big as it can
      val minh = View.MeasureSpec.getSize(w) + paddingBottom + paddingTop
      val h: Int = View.resolveSizeAndState(minh, heightMeasureSpec, 0)

      // 계산된 w, h 값

      Log.i("ArcView6", "* suggestedMinWidth: $suggestedMinimumWidth , suggestedMinHeight: $suggestedMinimumHeight")
      Log.i("ArcView6", "* paddingLeft: $paddingLeft, paddingTop: $paddingTop, paddingRight: $paddingRight, paddingBottom: $paddingBottom")
      Log.i("ArcView6", "* w: $w, minw: $minw, h: $h, minh: $minh")

      setMeasuredDimension(w, h)
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
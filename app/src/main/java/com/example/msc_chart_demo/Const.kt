package com.example.msc_chart_demo

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint

object Const {
    val colorWhite = "#FFFFFF"      // White
    val colorPurple = "#A8BBFF"
    val colorBlue = "#2554FD"
    val colorYellow = "#E8DC00"
    val colorOrange = "#FF6702"
    val yellowColor = "#FFD500"
    val greenColor = "#429F4E"

    val guideLineBlue = Paint().apply {
        strokeWidth = 4f
        color = Color.parseColor(colorBlue)
        style = Paint.Style.FILL
    }

    val guideLineYellow = Paint().apply {
        strokeWidth = 4f
        color = Color.parseColor(colorYellow)
        style = Paint.Style.FILL
    }

    val dotBlackStyle = Paint().apply {
        color = Color.parseColor("#000000")
        style = Paint.Style.FILL_AND_STROKE
    }

    val ballColors = listOf<Paint>(
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

    /** Paint : Text Style */
    val textSizeForDistance: Float = 32f
    val textSizeForClub: Float = 28f

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

    val teeBoxInnerStyle = Paint().apply {
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
}
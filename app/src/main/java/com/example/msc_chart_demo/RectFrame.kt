package com.example.msc_chart_demo

data class RectFrame(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val startAngle: Float = -60f,
    val sweepAngle: Float = -60f,
    val useCenter: Boolean = false
)

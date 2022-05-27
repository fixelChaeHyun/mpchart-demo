package com.example.msc_chart_demo

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate

class FanChartActivity : AppCompatActivity() {

    lateinit var chart : PieChart
    lateinit var chartLeft : PieChart
    lateinit var chartRight : PieChart

    lateinit var dataSet : PieDataSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fan)

        title = "FanChart(Angle)"

        /** PieChart A */
        chart = findViewById(R.id.chartA)
        chart.setBackgroundColor(Color.WHITE)

         moveOffScreen()

        chart.setUsePercentValues(true)
        chart.description.isEnabled = true
        val description = Description()
        description.text = "Launch Angle Directions"
        chart.description = description


        /** Do not Make a hole at this time */
        chart.isDrawHoleEnabled = false
        chart.isRotationEnabled = false


        /** Set the maxAngle for the chart */
        chart.maxAngle = 50f
        chart.rotationAngle = 180f + 45f

        setData()

        /** Legend Disable */
        chart.legend.isEnabled = false


        /** Styling a label for entries */
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(10f)


        /** ---------------------------- ChartLeft --------------------------------- */
//        chartLeft = findViewById(R.id.chartA)
//        chartLeft.setBackgroundColor(Color.WHITE)
//
//        chartLeft.setUsePercentValues(true)
//        chartLeft.description.isEnabled = false
//
//        /** Do not Make a hole at this time */
//        chartLeft.isDrawHoleEnabled = false
//        chartLeft.isRotationEnabled = false
//
//
//        /** Set the maxAngle for the chartLeft */
//        chartLeft.maxAngle = 20f
//        chartLeft.rotationAngle = 180f
//
//        setDataLeft(1, 23f)
//
//        /** Legend Disable */
//        chartLeft.legend.isEnabled = false
//
//
//        /** Styling a label for entries */
//        chartLeft.setEntryLabelColor(Color.WHITE)
//        chartLeft.setEntryLabelTextSize(10f)

    }

    private fun setData() {
        val dataList = arrayOf(25f, 60f, 15f)
        val pieEntry1 = PieEntry(dataList[0], "Left")
        val pieEntry2 = PieEntry(dataList[1], "Middle")
        val pieEntry3 = PieEntry(dataList[2], "Right")
        val entries = listOf(pieEntry1, pieEntry2, pieEntry3)

        dataSet = PieDataSet(entries, "")
        dataSet.valueTextSize = 15f
        dataSet.selectionShift = 50f
        dataSet.sliceSpace = 1f
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)

        val high : Highlight = Highlight(pieEntry2.value, 0, 0)
        chart.highlightValues(arrayOf(high))

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart.data = data
        chart.invalidate()
    }

    private fun moveOffScreen() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val offset = (height * 0.65).toInt() /* percent to move */
        val rlParams = chart.layoutParams as RelativeLayout.LayoutParams
        rlParams.setMargins(0, 0, 0, -offset)
        chart.layoutParams = rlParams
    }


}
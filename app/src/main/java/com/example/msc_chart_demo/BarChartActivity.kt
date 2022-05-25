package com.example.msc_chart_demo

import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF

class BarChartActivity : AppCompatActivity() {

    lateinit var chart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        title = "BarChart"

        val onValueSelectedRectF = RectF()
        chart = findViewById(R.id.chart)
        chart.setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e == null) return

                val bounds: RectF = onValueSelectedRectF
                chart.getBarBounds(e as BarEntry, bounds)
                val position = chart.getPosition(e, AxisDependency.LEFT)

                Log.i("bounds", bounds.toString())
                Log.i("position", position.toString())

                Log.i(
                    "x-index",
                    "low: " + chart.lowestVisibleX + ", high: "
                            + chart.highestVisibleX
                )

                MPPointF.recycleInstance(position)
            }

            override fun onNothingSelected() {

            }
        })

        chart.description.isEnabled = false

        chart.setDrawValueAboveBar(false)
        chart.setMaxVisibleValueCount(10)
        chart.setPinchZoom(false)
        chart.isDoubleTapToZoomEnabled = false
        chart.setDrawGridBackground(false)

        /** X축 설정 */
        val xAxisFormatter = DayAxisValueFormatter(chart)

        var xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = 12
        xAxis.valueFormatter = xAxisFormatter


        /** Y축 설정 */
        var leftAxis = chart.axisLeft
        leftAxis.labelCount = 4
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setDrawAxisLine(true)
        leftAxis.axisLineColor = Color.GRAY
        leftAxis.spaceTop = 15f
        leftAxis.spaceBottom = 15f
        leftAxis.axisMinimum = 0f

        var rightAxis = chart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.setLabelCount(0, true)
        rightAxis.setDrawZeroLine(false)

        /** Legend 설정 */
        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = Legend.LegendForm.DEFAULT

        /** MarkerView 설정 */
        val mv : XYMarkerView = XYMarkerView(this, xAxisFormatter)
        mv.chartView = chart
        chart.marker = mv

        val countX = 12
        val rangeY = 60f
        setData(countX, rangeY)

        chart.invalidate()
    }

    private fun setData(count: Int, range: Float) {
        val start = 1f
        val values = ArrayList<BarEntry>()
        var i = start.toInt()
        while (i < start + count) {
            val floatVal = (Math.random() * (range + 1)).toFloat()
            if (Math.random() * 100 < 25) {
                values.add(BarEntry(i.toFloat(), floatVal, resources.getDrawable(R.drawable.star)))
            } else {
                values.add(BarEntry(i.toFloat(), floatVal))
            }
            i++
        }
        val set1: BarDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "The year 2017")
            set1.setDrawIcons(false)
            val startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light)
            val startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light)
            val startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light)
            val endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark)
            val endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple)
            val endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark)
            val endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark)
            val endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark)


            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.9f
            chart.data = data
        }
    }
}
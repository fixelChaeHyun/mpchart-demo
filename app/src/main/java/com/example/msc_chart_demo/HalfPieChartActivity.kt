package com.example.msc_chart_demo

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class HalfPieChartActivity : AppCompatActivity() {

    lateinit var chart : PieChart

    private val parties = arrayOf(
        "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
        "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
        "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
        "Party Y", "Party Z"
    )

    private val clubs = arrayOf(
        "Par", "Birdie", "Eagle", "Bogey", "Bogey", "Par", "Bogey", "Par", "Birdie", "Birdie", "Eagle",
        "Par", "Birdie", "Eagle", "Bogey", "Eagle"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_half_pie)

        title = "PieChart(Custom)"

        chart = findViewById(R.id.chart)
        chart.setBackgroundColor(Color.WHITE)

        moveOffScreen()

        chart.setUsePercentValues(true)
        chart.description.isEnabled = false

        /** 중앙에 Hole 넣어서 도넛모양 만들기 */
        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(150)
        chart.holeRadius = 75f
        chart.transparentCircleRadius = 77f

        /** Fan Chart ... */
//        chart.holeRadius = 0f
//        chart.transparentCircleRadius = 0f

        /** 중앙에 문구 넣기 */
        chart.centerText = "Center Text\nGolf Score\nStatistics"
        chart.setDrawCenterText(true)
        chart.setCenterTextOffset(0f, -20f)


        chart.isRotationEnabled = false
        chart.isHighlightPerTapEnabled = true

        chart.maxAngle = 240f
        chart.rotationAngle = 150f

//        chart.maxAngle = 120f
//        chart.rotationAngle = 210f


        setData(4, 100f)

        chart.animateY(1400, Easing.EaseInOutQuad)

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.textSize = 12f
        l.xEntrySpace = 7f
        l.yEntrySpace = 5f
        l.yOffset = 120f

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(10f)
    }

    private fun setData(count: Int, range: Float) {
        val values = ArrayList<PieEntry>()
        for (i in 0 until count) {
            values.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
//                    parties.get(i % parties.size)
                    clubs.get(i % clubs.size)
                )
            )
        }
        val dataSet = PieDataSet(values, "Score Statistics")
        dataSet.valueTextSize = 15f
        dataSet.sliceSpace = 1f
        dataSet.selectionShift = 10f
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        //dataSet.setSelectionShift(0f);
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

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("Golf Score\nStatistics")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }
}
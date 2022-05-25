package com.example.msc_chart_demo

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.DecimalFormat

class XYMarkerView(context: Context, private var xAxisValueFormatter: ValueFormatter) : MarkerView(context, R.layout.custom_marker_view) {
    var tvContent : TextView = findViewById(R.id.tvContent)
    var format : DecimalFormat = DecimalFormat("###.0")

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e != null)
            tvContent.text = String.format("x: %s, y: %s", xAxisValueFormatter.getFormattedValue(e.x), format.format(e.y))

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat() - 30)

    }
}
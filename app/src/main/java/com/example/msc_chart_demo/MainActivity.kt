package com.example.msc_chart_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var button5: Button
    lateinit var button6: Button
    lateinit var button7: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById<Button>(R.id.btn_first)
        button1.setOnClickListener(this)
        button2 = findViewById(R.id.btn_second)
        button2.setOnClickListener(this)
        button3 = findViewById(R.id.btn_third)
        button3.setOnClickListener(this)
        button4 = findViewById(R.id.btn_fourth)
        button4.setOnClickListener(this)
        button5 = findViewById(R.id.btn_fifth)
        button5.setOnClickListener(this)
        button6 = findViewById(R.id.btn_sixth)
        button6.setOnClickListener(this)
        button7 = findViewById(R.id.btn_seventh)
        button7.setOnClickListener(this)


    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_first -> {
                Toast.makeText(this, "1번 데모", Toast.LENGTH_SHORT).show()
                val demoBarChart = Intent(this, BarChartActivity::class.java)
                startActivity(demoBarChart)
            }
            R.id.btn_second -> {
                Toast.makeText(this, "2번 데모", Toast.LENGTH_SHORT).show()
                val demoHalfPieChart = Intent(this, HalfPieChartActivity::class.java)
                startActivity(demoHalfPieChart)
            }
            R.id.btn_third -> {
                Toast.makeText(this, "3번 데모", Toast.LENGTH_SHORT).show()
                val demoFanChart = Intent(this, FanChartActivity::class.java)
                startActivity(demoFanChart)
            }
            R.id.btn_fourth -> {
                Toast.makeText(this, "4번 데모", Toast.LENGTH_SHORT).show()
                val arcDraw = Intent(this, DrawArcActivity::class.java)
                startActivity(arcDraw)
            }
            R.id.btn_fifth -> {
                Toast.makeText(this, "5번 데모", Toast.LENGTH_SHORT).show()
                val arcDraw = Intent(this, DrawArc2Activity::class.java)
                startActivity(arcDraw)
            }
            R.id.btn_sixth -> {
                Toast.makeText(this, "6번 데모", Toast.LENGTH_SHORT).show()
                val arcDraw = Intent(this, DrawArc3Activity::class.java)
                startActivity(arcDraw)
            }
            R.id.btn_seventh -> {
                Toast.makeText(this, "7번 데모", Toast.LENGTH_SHORT).show()
                val arcDraw = Intent(this, DrawArc4Activity::class.java)
                startActivity(arcDraw)
            }
        }
    }
}
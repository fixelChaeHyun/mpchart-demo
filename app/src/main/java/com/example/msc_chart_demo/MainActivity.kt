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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById<Button>(R.id.btn_first)
        button1.setOnClickListener(this)
        button2 = findViewById(R.id.btn_second)
        button2.setOnClickListener(this)
        button3 = findViewById(R.id.btn_third)
        button3.setOnClickListener(this)


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
        }
    }
}
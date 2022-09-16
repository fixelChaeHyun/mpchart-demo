package com.example.msc_chart_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.locks.ReentrantLock
import kotlin.system.measureTimeMillis

/** ReentrantLock /  Mutex 사용해서 Lock, Unlock 확인해보기 */
class LockTestActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName

    lateinit var lockButton : Button
    lateinit var unlockButton : Button
    lateinit var resultTextView1 : TextView
    lateinit var resultTextView2 : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_test)

        lockButton = findViewById(R.id.btn_lock)
        unlockButton = findViewById(R.id.btn_unlock)
        resultTextView1 = findViewById(R.id.tv_result)
        resultTextView2 = findViewById(R.id.tv_result2)


        lockButton.setOnClickListener {
            Log.d(TAG, " --- Lock clicked-() --- ")
            resultTextView2.text = "Locked"
            testRun("1button1")
        }

        unlockButton.setOnClickListener {
            Log.d(TAG, " --- Unlock clicked-() --- ")
            resultTextView2.text = "Unlocked"
            testRun("2button2")
        }



    }

    private fun testRun(log: String) {
        val mutex = Mutex()
        var counter = 0
        runBlocking {
            GlobalScope.massiveRun {
                mutex.withLock {
                    counter++
                    if (log == "1button1") {
                        Log.v(TAG, "[$log] counter: $counter")
                    } else {
                        Log.w(TAG, "[$log] counter: $counter")
                    }
//                    resultTextView1.text = "[$log] counter: $counter"
                }
            }
        }

        Log.d(TAG, "count : $counter")
    }

    suspend fun CoroutineScope.massiveRun(action: suspend () -> Unit) {
        val n = 100
        val k = 100
        val time = measureTimeMillis {
            val jobs = List(n) {
                launch {
                    repeat(k) {
                        delay(2)
                        action()
                    }
                    Log.w(TAG, "jobs: $it")
                }
            }

            jobs.forEach { it.join() }

        }
        Log.e(TAG, "[Finished] ${n*k} actions in $time ms")
    }
}
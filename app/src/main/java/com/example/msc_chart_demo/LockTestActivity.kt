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
import kotlin.concurrent.withLock
import kotlin.system.measureTimeMillis

/** ReentrantLock /  Mutex 사용해서 Lock, Unlock 확인해보기 */
class LockTestActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName

    companion object {
        var index = 0
    }


    lateinit var lockButton : Button
    lateinit var unlockButton : Button
    lateinit var cancelButton : Button
    lateinit var resultTextView1 : TextView
    lateinit var resultTextView2 : TextView

    val mutex = Mutex()
    val reentrantLock: ReentrantLock = ReentrantLock()

    val javaLock : Object = Object()

    val customCoroutineScope = CoroutineScope(Dispatchers.Default)

    lateinit var job : Job
    lateinit var jobs: List<Job>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_test)

        lockButton = findViewById(R.id.btn_lock)
        unlockButton = findViewById(R.id.btn_unlock)
        cancelButton = findViewById(R.id.btn_cancel)
        resultTextView1 = findViewById(R.id.tv_result)
        resultTextView2 = findViewById(R.id.tv_result2)


        lockButton.setOnClickListener {
            Log.d(TAG, " --- Lock clicked-() --- ")
            resultTextView2.text = "Locked"
            Log.e(TAG, " === Begin new testRun() ===")
            index++
            job = customCoroutineScope.launch {
                testRun("1button1", index)
            }
        }

        /** Timeout 10초, 10초 내에 unlockButton 을 눌러서 Lock 을 빠져나올 수 있는지 확인.
         * unlockButton 을 누르지 않아도 10초가 지나면 Lock 을 빠져나올 수 있는지 확인. */
        unlockButton.setOnClickListener {
            Log.d(TAG, " --- Unlock clicked-() --- ")
            try {
                notifyTask()
            } catch (e: Exception) {
                Log.e(TAG, "Object is not locked.")
            }
        }

        cancelButton.setOnClickListener {
            jobs.map {
                it.cancel()
            }
            job.cancel()
            Log.e(TAG, " CustomCoroutineScope Cancel!! ")
        }

    }

    @Synchronized
    private suspend fun testRun(log: String, add: Int) {
        var counter = 0
        try {

            // GlobalScope.massiveRun - RunBlocking
            val job = GlobalScope.massiveRun {

                counter++
                if (log == "1button1") {
                    if (counter % 200 == 0)
                        Log.v(TAG, "[${Thread.currentThread()}-$log][$add] counter: $counter")
                } else {
                    Log.w(TAG, "[$log] counter: $counter")
                }
                if (counter % 1000 == 0) {
                    Log.w(TAG, "--> Object Lock! ")
                    waitTask(1500)
                    Log.w(TAG, "--> Object Unlocked! ")
//                    reentrantLock.withLock {
//
//                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, ">>> TimeoutException : ${e.localizedMessage}")
        }

        Log.d(TAG, "count : $counter")
    }

    private fun waitTask(timeout: Long) {
        synchronized(javaLock) {
            try {
                javaLock.wait(timeout)
            } catch (e: Exception) {
                Log.e(TAG, "error-wait: ${e.localizedMessage}")
            }
        }
    }

    private fun notifyTask() {
        synchronized(javaLock) {
            try {
                javaLock.notifyAll()
            } catch (e: Exception) {
                Log.e(TAG, "error-notify: ${e.localizedMessage}")
            }
        }
    }

    //
    suspend fun CoroutineScope.massiveRun(action: suspend () -> Unit) = runBlocking {
        val n = 100
        val k = 100
        val time = measureTimeMillis {
            jobs = List(n) {
                launch {
                    repeat(k) {
                        delay(2)
                        action()
                    }
//                    Log.w(TAG, "jobs: $it")
                }
            }

            jobs.forEach { it.join() }

        }
        Log.e(TAG, "[Finished] ${n*k} actions in $time ms")
    }
}
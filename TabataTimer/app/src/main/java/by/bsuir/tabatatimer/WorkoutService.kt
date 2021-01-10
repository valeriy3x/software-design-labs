package by.bsuir.tabatatimer

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import by.bsuir.tabatatimer.data.viewdata.Sequence
import java.util.*
import kotlin.collections.ArrayList

class WorkoutService : Service() {
    private var countDownTimer: CountDownTimer? = null
    private var durations: ArrayList<Int>? = null
    private var steps: ArrayList<String>? = null
    private var currentStep = 0

    private var broadcastIntentData = Intent(broadcastAction)
    private var broadcastIntentStep = Intent(broadcastStepChanged)
    private var broadcastIntentEnd = Intent(broadcastEnd)

    override fun onBind(intent: Intent?): IBinder {
        return LocalBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        durations = intent?.getIntegerArrayListExtra(broadcastDurationsExtra)
        steps = intent?.getStringArrayListExtra(broadcastStepsExtra)

        val notification = NotificationCompat.Builder(this, TabataTimerApplication.notificationChannelId)
            .setContentTitle("Workout is Running")
            .setSmallIcon(R.drawable.ic_timer)
            .build()

        startForeground(1, notification)

        startTimer()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        super.onDestroy()
    }

    private fun startTimer() {
        durations?.let {
            if (currentStep > it.size - 1) {
                sendBroadcast(broadcastIntentEnd)
                return
            }
            countDownTimer =
                object : CountDownTimer(it[currentStep].toLong() * 1000, 1000) {
                    override fun onTick(remainingTime: Long) {
                        it[currentStep] = remainingTime.toInt() / 1000
                        broadcastIntentData.putExtra(
                            broadcastCountdownExtra,
                            remainingTime / 1000
                        )
                        broadcastIntentData.putExtra(
                            broadcastCurrentStepExtra,
                            steps?.get(currentStep)
                        )
                        sendBroadcast(broadcastIntentData)
                        tickSound()
                    }

                    override fun onFinish() {
                        ++currentStep
                        sendBroadcast(broadcastIntentStep)
                        endSound()
                        startTimer()
                    }
                }.start()
        }
    }


    fun nextStep() {
        countDownTimer?.cancel()

        durations?.let {
            if (currentStep < it.size - 1)
                ++currentStep
        }

        startTimer()
    }

    fun previousStep() {
        countDownTimer?.cancel()

        if (currentStep > 0)
            --currentStep

        startTimer()
    }

    fun stopTimer() {
        countDownTimer?.cancel()
    }

    fun resumeTimer() {
        startTimer()
    }

    private fun tickSound() {
        val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.tick)
        mediaPlayer.start()
    }

    private fun endSound() {
        val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.whistling)
        mediaPlayer.start()
    }

    inner class LocalBinder : Binder() {
        fun getService(): WorkoutService {
            return this@WorkoutService
        }
    }

    companion object {
        const val broadcastAction = "WorkoutService"
        const val broadcastDurationsExtra = "durations"
        const val broadcastStepsExtra = "steps"
        const val broadcastCountdownExtra = "countdown"
        const val broadcastCurrentStepExtra = "step"
        const val broadcastStepChanged = "changed"
        const val broadcastEnd = "end"
    }
}
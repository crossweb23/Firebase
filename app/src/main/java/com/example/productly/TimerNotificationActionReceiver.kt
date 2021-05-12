package com.example.productly

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.productly.util.NotificationUtil
import com.example.productly.util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        when(intent.action){
            AppConstants.ACTION_STOP -> {
                Timer.removeAlarm(context)
                PrefUtil.setTimerState(Timer.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = Timer.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                Timer.removeAlarm(context)
                PrefUtil.setTimerState(Timer.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = Timer.setAlarm(context, Timer.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(Timer.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstants.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 1500L
                val wakeUpTime = Timer.setAlarm(context, Timer.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(Timer.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}
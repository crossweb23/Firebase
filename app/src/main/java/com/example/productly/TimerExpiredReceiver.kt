package com.example.productly

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.productly.util.NotificationUtil
import com.example.productly.util.PrefUtil


class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //TODO: Show notif
        NotificationUtil.showTimerExpired(context)
        PrefUtil.setTimerState(Timer.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}
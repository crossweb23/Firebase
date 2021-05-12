package com.example.exampartone.db

import android.provider.BaseColumns

object DiaryPlannerContract {
    object DiaryPlanner:BaseColumns{
        const val TABLE_NAME = "entry"
        const val DATE = "date"
        const val TITLE = "title"
        const val DESC = "desc"
    }

    object DiaryPlannerPass:BaseColumns{
        const val TABLE_NAME = "pin_lock"
        const val PASS = "pass"
    }
}

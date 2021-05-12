package com.example.exampartone.db
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
//importing the Contract Class
import com.example.exampartone.db.DiaryPlannerContract.DiaryPlanner
import com.example.exampartone.db.DiaryPlannerContract.DiaryPlannerPass
import java.security.AccessControlContext

//table creation
private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${DiaryPlanner.TABLE_NAME} " +
            "(${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DiaryPlanner.DATE} TEXT," +
            "${DiaryPlanner.TITLE} TEXT," +
            "${DiaryPlanner.DESC} TEXT)"
//table creation
private const val SQL_CREATE_PASSWORD =
    "CREATE TABLE ${DiaryPlannerPass.TABLE_NAME}" +
            "(${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DiaryPlannerPass.PASS} TEXT)"

//table deletion
private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS" +
        "${DiaryPlanner.TABLE_NAME}" +
        "${DiaryPlannerPass.TABLE_NAME}"

class DiaryPlannerHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    //will be called when the db is created for the first time
    override fun onCreate(db:SQLiteDatabase){
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_PASSWORD)
    }
    //caled when database needs to be upgraded
    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int){
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    companion object{
        const val DATABASE_VERSION=1
        const val DATABASE_NAME="DiaryPlanner.db"
    }
}

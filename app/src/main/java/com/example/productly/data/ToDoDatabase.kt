package com.example.productly.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.productly.data.models.ToDoData
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities =  [ToDoData::class], version = 1 , exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase(){
    abstract  fun toDoDao () : ToDoDao

    //Companion Object  same as public static final class in Java
    companion object{
        //Writes to this field are immediately made visible to other threads
        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context : Context) : ToDoDatabase{

            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
                //When a thread call Scynchronized , it acquires the lock of that synchronized block. other threads
            // dont have permission to call the same sychronized block as long as previous thread which had acquired the lock does not release the lock
            synchronized (this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE  = instance
                return instance
            }
        }
    }


}
package com.example.productly.data.repository

import androidx.lifecycle.LiveData
import com.example.productly.data.ToDoDao
import com.example.productly.data.models.ToDoData

class TodoRepository(private val toDoDao: ToDoDao) {



    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAlldata()

    suspend fun  insertData(toDoData: ToDoData){

        toDoDao.insertData(toDoData)

    }
    suspend fun  updateData(toDoData : ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun  deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun  deleteAll(){
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery:String): LiveData<List<ToDoData>>{
        return toDoDao.searchDatabase(searchQuery)
    }
}
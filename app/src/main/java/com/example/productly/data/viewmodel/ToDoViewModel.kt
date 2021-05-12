package com.example.productly.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.productly.data.ToDoDatabase
import com.example.productly.data.models.ToDoData
import com.example.productly.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

// View Model role is to provide data to the UI and survive configuration changes.
// A viewmodel acts as a communication center between the respository and the UI
@OptIn(InternalCoroutinesApi::class)
class ToDoViewModel(application: Application): AndroidViewModel(application) {

    @InternalCoroutinesApi
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository : TodoRepository

    val getAllData: LiveData<List<ToDoData>>

    init {
        repository = TodoRepository(toDoDao)
        getAllData = repository.getAllData

    }

    fun  insertData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(toDoData)
        }
    }
    fun  updateData (toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(toDoData)
        }
    }

    fun deleteItem(toDoData: ToDoData)
    {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAll()
        }
    }
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>{
        return repository.searchDatabase(searchQuery)
    }
}
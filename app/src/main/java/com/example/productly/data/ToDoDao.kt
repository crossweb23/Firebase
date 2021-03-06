package com.example.productly.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.productly.data.models.ToDoData

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo_table ORDER BY id ASC ")
    fun getAlldata(): LiveData<List<ToDoData>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)

    // Suspend Keyword  Tell the Compiler that function will running inside coroutine
   suspend  fun insertData (toDoData: ToDoData)

    @Update
   suspend fun  updateData(toDoData: ToDoData)

   @Delete
   suspend fun  deleteItem(toDoData: ToDoData)

   @Query ( "DELETE  FROM todo_table ")
   suspend fun  deleteAll()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
   fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>
}
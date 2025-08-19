package com.example.mykoltinmulti.android

import androidx.lifecycle.ViewModel
import com.example.mykoltinmulti.Todo
import com.example.mykoltinmulti.TodoRepository

class TodoViewModel(
    private val repo: TodoRepository = TodoRepository()
) : ViewModel() {

    fun list(): List<Todo> = repo.list()

    fun add(text: String): Todo = repo.add(text)

    fun toggle(id: Long) = repo.toggle(id)

    fun remove(id: Long) = repo.remove(id)

    fun clear() = repo.clear()
}
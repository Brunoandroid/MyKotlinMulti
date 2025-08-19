package com.example.mykoltinmulti

data class Todo(
    val id: Long,
    val text: String,
    val done: Boolean
)

class TodoRepository {
    private val items = mutableListOf<Todo>()
    private var nextId = 1L

    fun list(): List<Todo> = items.toList()
    /** Convenience for Swift to avoid bridging casts. */
    fun listArray(): Array<Todo> = items.toTypedArray()

    fun add(text: String): Todo {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) throw IllegalArgumentException("Text must not be empty")
        val todo = Todo(id = nextId++, text = trimmed, done = false)
        items += todo
        return todo
    }

    fun toggle(id: Long): Todo? {
        val idx = items.indexOfFirst { it.id == id }
        if (idx == -1) return null
        val updated = items[idx].copy(done = !items[idx].done)
        items[idx] = updated
        return updated
    }

    fun remove(id: Long): Boolean {
        val idx = items.indexOfFirst { it.id == id }
        if (idx == -1) return false
        items.removeAt(idx)
        return true
    }

    fun clear() {
        items.clear()
    }
}

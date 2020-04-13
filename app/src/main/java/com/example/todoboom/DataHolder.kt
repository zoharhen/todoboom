package com.example.todoboom

import java.util.*

object DataHolder {

    private val items: HashMap<String, TodoItem> = HashMap()
    private var onListChangeListener: OnListChangeListener? = null

    fun addTodo(item: TodoItem) {
        items[item.id] = item
        onListChangeListener?.onListChange()
    }

    fun setTodoIsDone(id: String, isDone: Boolean) {
        items[id]?.setIsDone(isDone)
        onListChangeListener?.onListChange()
    }

    fun deleteTodoForever(id: String) {
        items.remove(id)
        onListChangeListener?.onListChange()
    }

    fun changeContent(id: String, content: String) {
        items[id]?.setContent(content)
        onListChangeListener?.onListChange()
    }

    fun getTodoFromId(id : String) : TodoItem {
        return items[id] ?: TodoItem("None")
    }

    fun getTodoFromPosition(position : Int) : TodoItem {
        return getAllTodos()[position]
    }

    fun getAllTodos() : List<TodoItem> {
        val list = mutableListOf<TodoItem>()
        list.addAll(items.values)
        list.sortBy { item -> item.creationTimestamp }
        return list
    }

    fun setListOfTodos(data: MutableList<TodoItem>)  {
        for (item in data) {
            items[item.id] = item
        }
        onListChangeListener?.onListChange()
    }

    fun getSize() : Int {
        return items.size
    }

    interface OnListChangeListener {
        fun onListChange()
    }

    fun setOnListChangeListener (onListChangeListener: OnListChangeListener) {
        this.onListChangeListener = onListChangeListener
    }
}
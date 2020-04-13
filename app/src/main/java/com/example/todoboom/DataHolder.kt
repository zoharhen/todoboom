package com.example.todoboom

import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import java.util.*


object DataHolder {

    private val items: HashMap<String, TodoItem> = HashMap()
    private var onListChangeListener: OnListChangeListener? = null
    private var fb: CollectionReference = FirebaseFirestore.getInstance().collection("todo_items")

    init {
        loadData()
    }

    fun addTodo(item: TodoItem) {
        items[item.id] = item
        val newItem: HashMap<String, Any> = hashMapOf("id" to item.id, "creationTimestamp" to item.creationTimestamp,
            "editTimestamp" to item.editTimestamp, "content" to item.getContent(), "isDone" to item.getIsDone()) // todo: replace hashSet with item? add empty constructor to TodoItem class
        fb.document(item.id).set(newItem)
            .addOnSuccessListener{ Log.i("DataHolder_addTodo", "OnSuccess: ItemId: ${item.id}");}
            .addOnFailureListener { Log.i("DataHolder_addTodo", "OnFailure: ItemId: ${item.id}");}
        onListChangeListener?.onListChange()
    }

    fun setTodoIsDone(id: String, isDone: Boolean) {
        items[id]?.setIsDone(isDone)
        fb.document(id).set(hashMapOf("isDone" to isDone, "editTimestamp" to items[id]?.editTimestamp), SetOptions.merge())
        onListChangeListener?.onListChange()
    }

    fun deleteTodoForever(id: String) {
        items.remove(id)
        fb.document(id).delete()
        onListChangeListener?.onListChange()
    }

    fun changeContent(id: String, content: String) {
        items[id]?.setContent(content)
        fb.document(id).set(hashMapOf("content" to content, "editTimestamp" to items[id]?.editTimestamp), SetOptions.merge())
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

    fun getSize() : Int {
        return items.size
    }

    private fun loadData()  {
        fb.get().addOnSuccessListener(OnSuccessListener<QuerySnapshot> { queryDocumentSnapshot ->
            for (doc in queryDocumentSnapshot) {
                items[doc.data["id"] as String] = TodoItem(doc.data["id"] as String,
                    doc.data["creationTimestamp"] as String, doc.data["editTimestamp"] as String,
                    doc.data["content"] as String, doc.data["isDone"] as Boolean)
            }
        })
        onListChangeListener?.onListChange()
    }

    interface OnListChangeListener {
        fun onListChange()
    }

    fun setOnListChangeListener (onListChangeListener: OnListChangeListener) {
        this.onListChangeListener = onListChangeListener
    }
}
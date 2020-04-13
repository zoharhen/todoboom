package com.example.todoboom

import com.google.gson.annotations.Expose
import java.text.SimpleDateFormat
import java.io.Serializable
import java.util.*

data class TodoItem(private var content: String, private var isDone: Boolean = false) : Serializable {

    constructor(id: String, creationTimestamp: String, editTimestamp: String, content: String, isDone: Boolean) : this(content, isDone) {
        this.id = id
        this.creationTimestamp = creationTimestamp
        this.editTimestamp = editTimestamp
    }

    @Expose(serialize = false, deserialize = false)
    private val DATE_FORMAT: String = "dd/M/yyyy hh:mm:ss" // issue with GSON lib when saving a SimpleDateFormat data member

    var id: String = UUID.randomUUID().toString()
    var creationTimestamp: String = SimpleDateFormat(DATE_FORMAT).format(Date())
    var editTimestamp: String = SimpleDateFormat(DATE_FORMAT).format(Date())

    fun getContent(): String {
        return content
    }

    fun setContent(content: String) {
        this.content = content
        editTimestamp = SimpleDateFormat(DATE_FORMAT).format(Date())
    }

    fun getIsDone(): Boolean {
        return isDone
    }

    fun setIsDone(isDone: Boolean) {
        this.isDone = isDone
        editTimestamp = SimpleDateFormat(DATE_FORMAT).format(Date())
    }
}
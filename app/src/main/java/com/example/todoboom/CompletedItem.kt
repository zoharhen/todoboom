package com.example.todoboom

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CompletedItem : AppCompatActivity() {

    private lateinit var content: TextView
    private lateinit var unMarkDoneButton: Button
    private lateinit var deleteButton: Button

    private lateinit var id: String
    lateinit var item: TodoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_item)

        id = intent.getStringExtra("id") ?:"None"
        item = DataHolder.getTodoFromId(id)

        content = findViewById(R.id.item_content)
        unMarkDoneButton = findViewById(R.id.disableIsDone)
        deleteButton = findViewById(R.id.delete)

        initButtons()
        content.text = item.getContent()
    }

    private fun initButtons() {

        unMarkDoneButton.setOnClickListener {
            DataHolder.setTodoIsDone(id, false)
            finish()
        }

        deleteButton.setOnClickListener {
            val alertdDialog = AlertDialog.Builder(it.context)
            alertdDialog.setTitle("Are You Sure to delete?")
            alertdDialog.setPositiveButton("Delete") { _, _ -> DataHolder.deleteTodoForever(item.id); finish() }
            alertdDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            alertdDialog.show()
        }

    }
}

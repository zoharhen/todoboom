package com.example.todoboom

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class UncompletedItem : AppCompatActivity() {

    private lateinit var content: EditText
    private lateinit var applyButton: Button
    private lateinit var itemTimestamp: TextView
    private lateinit var markDoneButton: Button

    private lateinit var id: String
    lateinit var item: TodoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uncompleted_item)

        id = intent.getStringExtra("id") ?:"None"
        item = DataHolder.getTodoFromId(id)

        content = findViewById(R.id.item_content)
        applyButton = findViewById(R.id.applyContent)
        itemTimestamp = findViewById(R.id.timestamp)
        markDoneButton = findViewById(R.id.markIsDone)

        initViews()
        initButtons()
    }

    private fun initViews() {
        content.setText(item.getContent())
        content.setHint(item.getContent())
        itemTimestamp.text = "Creation: ${item.creationTimestamp} \nLast edit: ${item.editTimestamp}"
    }

    private fun initButtons() {
        applyButton.setOnClickListener {
            val manager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(it.windowToken, 0)
            if (content.text.toString() != item.getContent()) {
                DataHolder.changeContent(id, content.text.toString())
                content.setHint(content.text.toString())
                itemTimestamp.text = "Creation: ${item.creationTimestamp} \nLast edit: ${item.editTimestamp}"
                Toast.makeText(applicationContext, "Todo content has changed successfully", Toast.LENGTH_LONG).show()
            }
            Toast.makeText(applicationContext, "No change has been made", Toast.LENGTH_LONG).show()
        }

        markDoneButton.setOnClickListener {
            DataHolder.setTodoIsDone(id, true)
            finish()
        }
    }

}

package com.example.todoboom

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class TodoItem(val description: String, var isDone: Boolean = false)

class MainActivity : AppCompatActivity() {

    private var adapter: TodoAdapter? = null
    private lateinit var recyclerView: RecyclerView
    var items: MutableList<TodoItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadList()

        initAdapter()
        initRecyclerview()
        initButton()

        Log.i("MainActivity_onCreate", "Items list size: ${items.size}");
    }

    private fun initButton() {
        val button: Button = findViewById(R.id.add_button)
        button.setOnClickListener { clearTextAndAddTodo(it) }
    }

    private fun initAdapter() {
        val onListChangeListener = object : TodoAdapter.OnListChangeListener {
            override fun OnListChange() {
                saveList()
            }
        }
        adapter = TodoAdapter(this, items, onListChangeListener)
    }

    private fun initRecyclerview() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        recyclerView.adapter = adapter
    }

    private fun clearTextAndAddTodo(it: View) {
        val inputText: EditText = findViewById(R.id.input_text)
        val lastText = inputText.text.toString()
        if (lastText == "") {
            Toast.makeText(this, "you can't create an empty TODO item!", Toast.LENGTH_SHORT).show()
            return
        }
        clearTextAndHideKeyboard(it, inputText)
        items.add(TodoItem(lastText))
        adapter?.notifyItemInserted(items.size- 1)
        saveList()
    }

    private fun clearTextAndHideKeyboard(it: View, inputText: EditText) {
        inputText.text.clear()
        val manager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(it.windowToken, 0)
    }

    private fun saveList() {
        val editor = getSharedPreferences("todoList", 0).edit()
        val json = Gson().toJson(items)
        editor.putString("userInput", json)
        editor.apply()
    }

    private fun loadList() {
        val json = getSharedPreferences("todoList", 0).getString("userInput", null)
        val type = object : TypeToken<MutableList<TodoItem>?>() {}.type
        if (json != null) {
            items = Gson().fromJson(json, type)
        }
    }
}

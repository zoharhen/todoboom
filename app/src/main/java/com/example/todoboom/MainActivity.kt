package com.example.todoboom

import android.app.Activity
import android.content.Intent
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
import kotlinx.android.synthetic.main.todo_item.view.*

class MainActivity : AppCompatActivity(), TodoAdapter.ItemClickListener {

    private var adapter: TodoAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private var itemsHolder: DataHolder = DataHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = TodoAdapter(this)
        initRecyclerview()
        initDataListener()
        initButton()

        Log.i("MainActivity_onCreate", "Items list size: ${itemsHolder.getSize()}");
    }

    private fun initButton() {
        val button: Button = findViewById(R.id.add_button)
        button.setOnClickListener { clearTextAndAddTodo(it) }
    }

    private fun initRecyclerview() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        recyclerView.adapter = adapter
    }

    private fun initDataListener() {
        val onListChangeListener = object : DataHolder.OnListChangeListener {
            override fun onListChange() {
                adapter?.notifyDataSetChanged();
            }
        }
        itemsHolder.setOnListChangeListener(onListChangeListener)
    }

    private fun clearTextAndAddTodo(it: View) {
        val inputText: EditText = findViewById(R.id.input_text)
        val lastText = inputText.text.toString()
        if (lastText == "") {
            Toast.makeText(this, "you can't create an empty TODO item!", Toast.LENGTH_SHORT).show()
            return
        }
        clearTextAndHideKeyboard(it, inputText)
        val newItem = TodoItem(lastText)
        itemsHolder.addTodo(newItem)
        adapter?.notifyDataSetChanged()
    }

    private fun clearTextAndHideKeyboard(it: View, inputText: EditText) {
        inputText.text.clear()
        val manager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(it.windowToken, 0)
    }

    override fun onItemClick(view: View, id: String) {
        val nextActivity = if (view.checkBox.isChecked) CompletedItem::class.java else UncompletedItem::class.java
        val intent = Intent(this, nextActivity)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }

}

package com.example.todoboom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter internal constructor(context: Context, data: MutableList<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private val items: MutableList<TodoItem> = data
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val cnt: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: TodoItem = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var description: TextView = itemView.findViewById(R.id.textView)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(todo: TodoItem) {
            description.text = todo.description
            checkBox.isChecked = todo.isDone

            itemView.setOnClickListener{
                if (!checkBox.isChecked) {
                    checkBox.isChecked = true
                    Toast.makeText(cnt, "TODO ${description.text} is now DONE. BOOM!", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}

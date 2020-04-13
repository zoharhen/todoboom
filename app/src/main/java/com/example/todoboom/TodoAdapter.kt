package com.example.todoboom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TodoAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private val cnt: Context = context
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: TodoItem = DataHolder.getTodoFromPosition(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return DataHolder.getSize()
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var description: TextView = itemView.findViewById(R.id.textView)
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(todo: TodoItem) {
            description.text = todo.getContent()
            checkBox.isChecked = todo.getIsDone()

            itemView.setOnClickListener { (cnt as ItemClickListener).onItemClick(itemView, todo.id); notifyDataSetChanged() }
        }
    }

    interface ItemClickListener {
        fun onItemClick(view: View, id: String)
    }

}

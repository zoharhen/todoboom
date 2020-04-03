package com.example.todoboom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.add_button)
        button.setOnClickListener { ClearTextFromEditText(it) }
    }

    fun ClearTextFromEditText(it: View) {
        val inputText: EditText = findViewById(R.id.input_text)
        val textView: TextView = findViewById(R.id.text_view)
        val last_text = inputText.text.toString()
        inputText.text.clear()
        textView.text = last_text
    }
}

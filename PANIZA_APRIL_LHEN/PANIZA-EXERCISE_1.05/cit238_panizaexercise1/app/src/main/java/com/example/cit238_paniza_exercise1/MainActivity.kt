package com.example.cit238_paniza_exercise1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.cit238_paniza_exercise1.R
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterButton = findViewById<Button>(R.id.enter_button)
        val greetingDisplay = findViewById<TextView>(R.id.greeting_display)
        val firstNameInput = findViewById<TextInputEditText>(R.id.first_name)
        val lastNameInput = findViewById<TextInputEditText>(R.id.last_name)

        enterButton?.setOnClickListener {
            val firstName = firstNameInput?.text.toString().trim()
            val lastName = lastNameInput?.text.toString().trim()

            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                val nameToDisplay = "$firstName $lastName"
                greetingDisplay?.text = getString(R.string.welcome_to_the_app, nameToDisplay)
            } else {
                Toast.makeText(this, getString(R.string.please_enter_a_name), Toast.LENGTH_LONG).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                    show()
                }
            }
        }
    }
}
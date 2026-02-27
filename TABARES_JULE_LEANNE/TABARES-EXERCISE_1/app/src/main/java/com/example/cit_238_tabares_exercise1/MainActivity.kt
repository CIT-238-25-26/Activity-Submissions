package com.example.cit_238_tabares_exercise1

import android.os.Bundle
import android.view.Gravity // <--- 1. ADDED THIS IMPORT
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.main)

        // Get references to your views
        val firstNameInput = findViewById<TextInputEditText>(R.id.first_name)
        val lastNameInput = findViewById<TextInputEditText>(R.id.last_name)
        val enterButton = findViewById<MaterialButton>(R.id.enter_button)
        val greetingDisplay = findViewById<TextView>(R.id.greeting_display)

        // Set up button click listener
        enterButton.setOnClickListener {
            val firstName = firstNameInput.text.toString().trim()
            val lastName = lastNameInput.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty()) {
                // 2. UPDATED TOAST BLOCK (Centers the popup)
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_a_name),
                    Toast.LENGTH_LONG
                ).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                    show()
                }
            } else {
                val greeting = "${getString(R.string.welcome_to_the_app)} $firstName $lastName!"
                greetingDisplay.text = greeting
            }
        }
    }
}
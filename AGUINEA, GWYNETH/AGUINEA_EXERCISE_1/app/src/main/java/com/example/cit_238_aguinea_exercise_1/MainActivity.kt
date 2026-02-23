package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get references to UI elements
        val enterButton = findViewById<Button>(R.id.enter_button)
        val firstNameEditText = findViewById<TextInputEditText>(R.id.first_name)
        val lastNameEditText = findViewById<TextInputEditText>(R.id.last_name)
        val greetingDisplay = findViewById<TextView>(R.id.greeting_display)

        // Set click listener for the button
        enterButton.setOnClickListener {
            // Get values from input fields and trim whitespace
            val firstName = firstNameEditText.text?.toString()?.trim() ?: ""
            val lastName = lastNameEditText.text?.toString()?.trim() ?: ""

            // Validate input
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                // Create the full name
                val fullName = "$firstName $lastName"

                // Display the greeting
                val greeting = "${getString(R.string.welcome_to_the_app)} $fullName!"
                greetingDisplay.text = greeting
            } else {
                // Show error message
                val toast = Toast.makeText(
                    this,
                    getString(R.string.please_enter_a_name),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }
}
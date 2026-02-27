package com.example.cit_238_cabalonga_exercise1

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cit_238_cabalonga_exercise1.R
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        findViewById<Button>(R.id.enter_button)?.setOnClickListener {

            val greetingDisplay = findViewById<TextView>(R.id.greeting_display)

            val firstName = findViewById<TextInputEditText>(R.id.first_name)
                ?.text?.toString()?.trim().orEmpty()

            val lastName = findViewById<TextInputEditText>(R.id.last_name)
                ?.text?.toString()?.trim().orEmpty()

            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                val nameToDisplay = "$firstName $lastName"
                greetingDisplay?.text =
                    "${getString(R.string.welcome_to_the_app)} $nameToDisplay!"
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.please_enter_a_name),
                    Toast.LENGTH_LONG
                ).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                    show()
                }
            }
        }
    }
}

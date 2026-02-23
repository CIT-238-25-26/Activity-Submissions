package com.example.cit_238_bartonico_exercise_1

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use the XML layout
        setContentView(R.layout.activity_main)

        // Get the views from the layout
        val enterButton = findViewById<MaterialButton>(R.id.enterbutton)
        val greetingDisplay = findViewById<TextView>(R.id.greetingdisplay)
        val firstNameInput = findViewById<TextInputEditText>(R.id.firstname)
        val lastNameInput = findViewById<TextInputEditText>(R.id.lastname)

        // Handle button click
        enterButton.setOnClickListener {
            val firstName = firstNameInput.text?.toString()?.trim() ?: ""
            val lastName = lastNameInput.text?.toString()?.trim() ?: ""

            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                val nameToDisplay = "$firstName $lastName"
                greetingDisplay.text =
                    getString(R.string.welcometotheapp, nameToDisplay)
            } else {
                val toast = Toast.makeText(
                    this,
                    getString(R.string.pleaseenteraname),
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }
    }
}

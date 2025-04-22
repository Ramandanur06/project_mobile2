package com.example.mybooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        registerPassword = findViewById(R.id.registerPassword)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val nameText = name.text.toString()
            val emailText = email.text.toString()
            val passwordText = registerPassword.text.toString()

            if (nameText.isNotEmpty() && emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                // Simpan nama, email, dan password ke SharedPreferences
                val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("name", nameText)  // Menyimpan nama
                    putString("username", emailText)  // Menyimpan email
                    putString("password", passwordText)  // Menyimpan password
                    apply()
                }

                Toast.makeText(this, "Registrasi sukses", Toast.LENGTH_SHORT).show()

                // Pindah ke halaman login setelah register
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Isi semua field dulu dong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

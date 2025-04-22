package com.example.mybooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cek apakah user sudah login
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // Kalau sudah login, langsung ke BooksListActivity
        if (isLoggedIn) {
            navigateToBooksList()
            return
        }

        // Inisialisasi komponen UI
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)

        // Aksi tombol login
        loginButton.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()

            val savedUsername = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (enteredUsername == savedUsername && enteredPassword == savedPassword) {
                // Update status login
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                // Login sukses, arahkan ke BooksListActivity
                navigateToBooksList()
            } else {
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }

        // Pindah ke halaman register
        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun navigateToBooksList() {
        val intent = Intent(this, BooksListActivity::class.java)
        startActivity(intent)
        finish() // Menutup MainActivity agar tidak kembali lagi
    }
}

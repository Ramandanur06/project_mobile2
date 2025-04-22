package com.example.mybooks

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var profilePic: ImageView
    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var bookCountText: TextView
    private lateinit var logoutButton: Button
    private lateinit var editButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Bind views
        profilePic = findViewById(R.id.imageViewProfilePic)
        nameText = findViewById(R.id.textViewProfileName)
        emailText = findViewById(R.id.textViewProfileEmail)
        bookCountText = findViewById(R.id.textViewBookCount)
        logoutButton = findViewById(R.id.buttonLogout)
        editButton = findViewById(R.id.buttonEditProfile)

        // Load user data
        loadProfileData()

        // Logout action
        logoutButton.setOnClickListener {
            getSharedPreferences("user_data", Context.MODE_PRIVATE).edit()
                .putBoolean("isLoggedIn", false)
                .apply()

            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
        }

        // Edit profile action
        editButton.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadProfileData() // Refresh data ketika kembali dari edit profile
    }

    private fun loadProfileData() {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "Si Pembaca Buku") ?: "Si Pembaca Buku"
        val userEmail = sharedPreferences.getString("username", "email@example.com") ?: "email@example.com"
        val imageUri = sharedPreferences.getString("profile_image_uri", null)

        nameText.text = userName
        emailText.text = userEmail
        bookCountText.text = "Buku Dibaca: ${BookStorage.bookList.size}"

        if (!imageUri.isNullOrEmpty()) {
            profilePic.setImageURI(Uri.parse(imageUri))
        } else {
            profilePic.setImageResource(R.drawable.ic_baseline_account_box_24)
        }
    }
}

package com.example.mybooks

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var imageView: ImageView
    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        nameEditText = findViewById(R.id.editName)
        emailEditText = findViewById(R.id.editEmail)
        saveButton = findViewById(R.id.buttonSave)
        imageView = findViewById(R.id.imageViewProfileEdit)

        val prefs = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        nameEditText.setText(prefs.getString("name", ""))
        emailEditText.setText(prefs.getString("username", ""))

        // Set image if there's any saved image URI
        val savedImageUri = prefs.getString("profile_image_uri", null)
        if (savedImageUri != null) {
            imageView.setImageURI(Uri.parse(savedImageUri))
        }

        // Click listener to pick an image
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE)
        }

        // Save changes
        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newEmail = emailEditText.text.toString()

            // Save new profile data
            prefs.edit().apply {
                putString("name", newName)
                putString("username", newEmail)
                imageUri?.let {
                    putString("profile_image_uri", it.toString()) // Save image URI
                }
                apply()
            }

            Toast.makeText(this, "Profil berhasil disimpan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
            // Gambar udah ditampilin, simpan URI ke SharedPreferences saat save
        }
    }
}

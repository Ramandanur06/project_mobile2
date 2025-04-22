package com.example.mybooks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        val bookTitleEditText = findViewById<EditText>(R.id.editTextBookTitle)
        val bookAuthorEditText = findViewById<EditText>(R.id.editTextBookAuthor)
        val saveButton = findViewById<Button>(R.id.buttonSaveBook)

        val isEdit = intent.getBooleanExtra("IS_EDIT", false)
        val position = intent.getIntExtra("BOOK_POSITION", -1)
        val initialTitle = intent.getStringExtra("BOOK_TITLE")
        val initialAuthor = intent.getStringExtra("BOOK_AUTHOR")

        if (isEdit) {
            bookTitleEditText.setText(initialTitle)
            bookAuthorEditText.setText(initialAuthor)
            saveButton.text = "Simpan Perubahan"
        }

        saveButton.setOnClickListener {
            val bookTitle = bookTitleEditText.text.toString()
            val bookAuthor = bookAuthorEditText.text.toString()

            if (bookTitle.isNotEmpty() && bookAuthor.isNotEmpty()) {
                if (isEdit && position != -1) {
                    // Update buku langsung di list
                    BookStorage.bookList[position] = Book(bookTitle, bookAuthor)
                    Toast.makeText(this, "📘 Buku berhasil diupdate!", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                } else {
                    // Kirim data buku balik ke BooksListActivity (biar dia yang nambahin)
                    val resultIntent = Intent().apply {
                        putExtra("BOOK_TITLE", bookTitle)
                        putExtra("BOOK_AUTHOR", bookAuthor)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    Toast.makeText(this, "✅ Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "⚠️ Isi semua data dulu lah, bro.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.example.mybooks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // Ambil data dari intent
        val title = intent.getStringExtra("BOOK_TITLE")
        val author = intent.getStringExtra("BOOK_AUTHOR")
        val position = intent.getIntExtra("BOOK_POSITION", -1)

        // Bind UI - yang ini BENER
        val titleTextView = findViewById<TextView>(R.id.bookTitle) // ganti dari textViewTitle
        val authorTextView = findViewById<TextView>(R.id.bookAuthor) // ganti dari textViewAuthor
        val editButton = findViewById<Button>(R.id.buttonEditBook)
        val deleteButton = findViewById<Button>(R.id.buttonDeleteBook)
        val readButton = findViewById<Button>(R.id.buttonReadBook)

        // Set teks
        titleTextView.text = title ?: "Judul Tidak Diketahui"
        authorTextView.text = author ?: "Penulis Tidak Diketahui"

        // Tombol Edit
        editButton.setOnClickListener {
            val editIntent = Intent(this, AddBookActivity::class.java).apply {
                putExtra("BOOK_TITLE", title)
                putExtra("BOOK_AUTHOR", author)
                putExtra("BOOK_POSITION", position)
                putExtra("IS_EDIT", true)
            }
            startActivity(editIntent)
            finish()
        }

        // Tombol Hapus
        deleteButton.setOnClickListener {
            if (position in BookStorage.bookList.indices) {
                BookStorage.bookList.removeAt(position)
                Toast.makeText(this, "📚 Buku berhasil dihapus", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "❌ Posisi buku tidak valid", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        // Tombol Baca
        readButton.setOnClickListener {
            if (!title.isNullOrEmpty() && !author.isNullOrEmpty()) {
                val readIntent = Intent(this, ReadBookActivity::class.java).apply {
                    putExtra("BOOK_TITLE", title)
                    putExtra("BOOK_AUTHOR", author)
                }
                startActivity(readIntent)
            } else {
                Toast.makeText(this, "⚠️ Data buku tidak lengkap buat dibaca", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

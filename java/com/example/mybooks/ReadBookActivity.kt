package com.example.mybooks

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReadBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_book)

        // Ambil data dari intent (harus konsisten sama pengiriman di BookDetailActivity)
        val bookTitle = intent.getStringExtra("BOOK_TITLE") ?: "Judul Tidak Diketahui"
        val bookAuthor = intent.getStringExtra("BOOK_AUTHOR") ?: "Penulis Tidak Diketahui"

        // Bind view dari layout
        val titleView = findViewById<TextView>(R.id.readBookTitle)
        val contentView = findViewById<TextView>(R.id.readBookContent)

        // Set judul dan isi dummy
        titleView.text = bookTitle
        contentView.text = """
            Ini adalah isi dari buku "$bookTitle" yang ditulis oleh $bookAuthor.
            
            Contoh isi bacaan:
            - Bab 1: Awal perjalanan
            - Bab 2: Tantangan di tengah jalan
            - Bab 3: Akhir yang memukau

            Kamu bisa nambahin isi asli buku di sini nanti kalau udah disiapin.
        """.trimIndent()
    }
}

package com.example.mybooks

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class BooksListActivity : AppCompatActivity() {

    private lateinit var bookListView: ListView
    private lateinit var searchEditText: EditText
    private lateinit var adapter: BookAdapter

    private var allBooks = mutableListOf<Book>()

    // Launcher buat nambah buku
    private val addBookLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val title = data?.getStringExtra("BOOK_TITLE")
            val author = data?.getStringExtra("BOOK_AUTHOR")

            if (!title.isNullOrEmpty() && !author.isNullOrEmpty()) {
                BookStorage.bookList.add(Book(title, author))
                refreshBookList()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)

        bookListView = findViewById(R.id.bookListView)
        searchEditText = findViewById(R.id.searchEditText)

        // Inisialisasi list kosong (biar gak dobel)
        allBooks = mutableListOf()

        // Set adapter
        adapter = BookAdapter(this, allBooks)
        bookListView.adapter = adapter

        // Tombol tambah buku
        findViewById<ImageView>(R.id.addBookButton).setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            addBookLauncher.launch(intent)
        }

        // Tombol profil
        findViewById<ImageView>(R.id.buttonProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Klik buku ke detail
        bookListView.setOnItemClickListener { _, _, position, _ ->
            val selectedBook = allBooks[position]
            val intent = Intent(this, BookDetailActivity::class.java).apply {
                putExtra("BOOK_TITLE", selectedBook.title)
                putExtra("BOOK_AUTHOR", selectedBook.author)
                putExtra("BOOK_POSITION", BookStorage.bookList.indexOf(selectedBook))
            }
            startActivity(intent)
        }

        // Realtime search
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterBooks(s.toString())
            }
        })

        // Panggil awal biar list langsung muncul
        refreshBookList()
    }

    override fun onResume() {
        super.onResume()
        refreshBookList()
    }

    // Buat refresh list dan filter lagi
    private fun refreshBookList() {
        allBooks.clear()
        allBooks.addAll(BookStorage.bookList)
        filterBooks(searchEditText.text.toString())
    }

    // Buat filter buku berdasarkan judul/penulis
    private fun filterBooks(keyword: String) {
        val filtered = BookStorage.bookList.filter {
            it.title.contains(keyword, ignoreCase = true) ||
                    it.author.contains(keyword, ignoreCase = true)
        }
        allBooks.clear()
        allBooks.addAll(filtered)
        adapter.notifyDataSetChanged()
    }
}

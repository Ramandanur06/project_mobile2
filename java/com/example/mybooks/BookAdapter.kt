package com.example.mybooks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class BookAdapter(context: Context, private val books: List<Book>) : ArrayAdapter<Book>(context, 0, books), Filterable {

    private val originalBooks: List<Book> = books.toList() // Menyimpan daftar asli
    private var filteredBooks: List<Book> = books // Daftar yang sudah difilter

    override fun getCount(): Int {
        return filteredBooks.size // Menggunakan daftar yang sudah difilter
    }

    override fun getItem(position: Int): Book? {
        return filteredBooks[position] // Menggunakan daftar yang sudah difilter
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val book = getItem(position) ?: return convertView!!
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_book, parent, false)

        val titleTextView = view.findViewById<TextView>(R.id.bookTitle)
        val authorTextView = view.findViewById<TextView>(R.id.bookAuthor)

        titleTextView.text = book.title
        authorTextView.text = book.author

        return view
    }

    // Implementasi filter untuk SearchView
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (constraint.isNullOrEmpty()) {
                    filterResults.values = originalBooks // Jika query kosong, tampilkan semua buku
                    filterResults.count = originalBooks.size
                } else {
                    val queryString = constraint.toString().toLowerCase()
                    val filteredList = originalBooks.filter {
                        it.title.toLowerCase().contains(queryString) ||
                                it.author.toLowerCase().contains(queryString)
                    }
                    filterResults.values = filteredList
                    filterResults.count = filteredList.size
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    filteredBooks = results.values as List<Book>
                    notifyDataSetChanged() // Update list setelah filtering
                }
            }
        }
    }
}

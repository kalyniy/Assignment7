package edu.temple.audiobb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
class BookSearchActivity : AppCompatActivity() {
    lateinit var editField: EditText
    lateinit var btnCancel: Button
    lateinit var btnSearch: Button

    lateinit var requestQueue: RequestQueue
    val url = "https://kamorris.com/lab/cis3515/search.php?term="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_search)


        editField = findViewById(R.id.editField)
        btnCancel = findViewById(R.id.btnCancel)
        btnSearch = findViewById(R.id.btnSearch)
        requestQueue = Volley.newRequestQueue(this)
        btnCancel.setOnClickListener {
            val i = Intent()
            setResult(RESULT_OK, i)
            this.finish()
        }
        btnSearch.setOnClickListener(View.OnClickListener {

            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url + editField.text.toString(), null,
                { response ->

                    val intent = Intent()
                    val returnBook: BookList = jsonToBookList(response)
                    intent.putExtra("result", response.toString())
                    intent.putExtra("SearchBooks", returnBook)

                    setResult(RESULT_OK, intent)
                    finish()

                }
            ) { error ->
            }
            requestQueue.add(jsonArrayRequest)
        })
    }

    /*
        val title: String,
        val author: String,
        val id: Int,
        val coverURL: String
     */
    private fun jsonToBookList(jsonArray: JSONArray): BookList {
        val bookList = BookList()
        var book: JSONObject
        for (i in 0 until jsonArray.length()) {
            try {
                book = jsonArray.getJSONObject(i)
                val temp = Book(
                    book.getString("title"),
                    book.getString("author"),
                    book.getInt("id"),
                    book.getString("cover_url")
                )
                bookList.add(temp)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return bookList

    }
}

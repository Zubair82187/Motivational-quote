package com.motivationalquote

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.motivationalquote.recyclerview.QuoteAdapter
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            makeRequest()
        }
        makeRequest()
    }

    private fun setRecyclerView(list:ArrayList<String>) {
        recyclerView = findViewById(R.id.quotesRV)
        val layooutManager = LinearLayoutManager(this)
        val adapter = QuoteAdapter(list, this)
        recyclerView.layoutManager = layooutManager
        recyclerView.adapter = adapter
    }

    private fun makeRequest() {
        //instantiating request queue that is default
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.quotable.io/quotes/random?limit=10"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonArray = JSONArray(response)

                for(i in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(i)
                    val quote = jsonObject.getString("content")
                    Log.d("Quote",quote)
                    list.add(quote)
                }
                swipeRefreshLayout.isRefreshing = false
                setRecyclerView(list)
            },
            {
                Toast.makeText(this, "Volley error", Toast.LENGTH_SHORT).show()
            })

        queue.add(stringRequest)
    }

}
package com.ankurtambe.volleyresponseapp

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var lv: ListView
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.response_lv)

        requestQueue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            "https://jsonplaceholder.typicode.com/todos",
            null,
            { response ->
                var list = ArrayList<String>()
                try {
                    for (i in 0 until response.length()) {
                        list.add(
                            "UserId : " + response.getJSONObject(i).getInt("userId") + "\n" +
                                    "Id : #" + response.getJSONObject(i).getInt("id")
                                    + "\n" + "Title : " + response.getJSONObject(i)
                                .getString("title") +
                                    "\n" + "Completed : " + response.getJSONObject(i)
                                .getBoolean("completed")
                        )
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                var adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
                lv.adapter = adp

            }) {
            Log.d("Ankur", "Something went wrong")
            Toast.makeText(this@MainActivity, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
        requestQueue.add(jsonArrayRequest)
    }
}
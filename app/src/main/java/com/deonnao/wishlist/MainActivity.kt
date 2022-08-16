package com.deonnao.wishlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.security.KeyStore


class MainActivity : AppCompatActivity() {
    var listOfItems = mutableListOf<Wish>()
    lateinit var adapter: WishlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : WishlistAdapter.onLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //Remove the item from the list
                listOfItems.removeAt(position)
                //Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()
            }
        }

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.rvList)
        // Create adapter passing in the listOfItems and the long click listener
        adapter = WishlistAdapter(listOfItems, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //values to reference the views from the layout file
        val itemName = findViewById<EditText>(R.id.etItemName)
        val itemPrice = findViewById<EditText>(R.id.etPrice)
        val itemURL = findViewById<EditText>(R.id.etURL)

        findViewById<Button>(R.id.submitBtn).setOnClickListener {
            //Grab the text that the user inputs
            val item = itemName.text.toString()
            val price = itemPrice.text.toString()
            val url = itemURL.text.toString()
            //use the data model to get all of the wish items
            val wishItems = Wish(item, price, url)
            //add the wish items to listOfItems
            listOfItems.add(wishItems)
            //notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfItems.size - 1)
            //Reset text field after user adds item to the wishlist
            itemName.setText("")
            itemPrice.setText("")
            itemURL.setText("")
            //hide the keyboard after user adds item to the wishlist
            itemName.hideKeyboard()

        }
    }

    //function to hide the keyboard after the user enters their desired item to the wishlist
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
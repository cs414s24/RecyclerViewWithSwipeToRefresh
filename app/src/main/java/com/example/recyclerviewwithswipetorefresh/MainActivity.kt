package com.example.recyclerviewwithswipetorefresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var refreshCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Store the the recyclerView widget in a variable
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        // specify an viewAdapter for the dataset (we use dummy data containing 20 contacts)
        recyclerView.adapter = MyRecyclerAdapter(generateContact(20))

        // use a linear layout manager, you can use different layouts as well
        recyclerView.layoutManager = LinearLayoutManager(this)

        // if you want, you can make the layout of the recyclerview horizontal as follows
        //recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        // Add a divider between rows -- Optional
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)


        // Setup refresh listener which triggers new data loading
        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeToRefresh.setOnRefreshListener {

            // Let's assume that we want to add 2 items each the user swipes up to refresh
            // More realistically, the data should come from a server or a database
            refreshCount += 2
            Log.d(TAG, "Added $refreshCount items...")

            // Update the adapter with new data. Alternatively, notifyDataSetChanged() can be called
            recyclerView.adapter = MyRecyclerAdapter(generateContact(20 + refreshCount))

            // Make sure to set isRefreshing false to hide the refreshing animation
            swipeToRefresh.isRefreshing = false

        }
    }


    // A helper function to create specified amount of dummy contact data
    private fun generateContact(size: Int) : ArrayList<Contact>{
        // Create an ArrayList to store contacts
        val contacts = ArrayList<Contact>()

        // The for loop will generate [size] amount of contact data and store in a list
        for (i in 1..size) {
            // i is concatenated to the end of the url to generate unique image each time
            val imageLink = "https://picsum.photos/150?random=$i"
            val person = Contact("John Doe-$i", i, imageLink)
            contacts.add(person)
        }

        // Reverse the array so that the newly added data can be seen at the top of the list
        contacts.reverse()

        // return the list of contacts
        return contacts
    }
}
package com.example.recyclerviewwithswipetorefresh

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private const val TAG = "MyRecyclerAdapter"

class MyRecyclerAdapter(private val contacts: ArrayList<Contact>): RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {

    var count = 1 //This variable is used for just testing purpose to understand how RecyclerView works

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // inner keyword allows to carry a reference to an outer class,
    // and can access outer class members (e.g.,  contacts array as shown below).
     inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // This class will represent a single row in our recyclerView list
        // This class also allows caching views and reuse them
        // Each MyViewHolder object keeps a reference to 3 view items in our row_item.xml file
        val personName = itemView.findViewById<TextView>(R.id.person_name)
        val personAge = itemView.findViewById<TextView>(R.id.person_age)
        val personImage = itemView.findViewById<ImageView>(R.id.person_image)


        // init block is used as part of the primary constructor (It is executed when a ViewHolder instance is created)
        init {
            // Attach a click listener to the entire row view
            itemView.setOnClickListener {
                // adapterPosition refers to the position of the item associated with the ViewHolder within the RecyclerView's dataset
                val selectedItem = adapterPosition
                Toast.makeText(itemView.context, "You clicked on $selectedItem", Toast.LENGTH_SHORT).show()
            }


            // Set onLongClickListener to show a toast message and remove the selected row item from the list
            // Make sure to add inner in front of MyViewHolder class to get access of object of outer class such as contacts array
            itemView.setOnLongClickListener {

                val selectedItem = adapterPosition
                contacts.removeAt(selectedItem)
                notifyItemRemoved(selectedItem)
                Toast.makeText(itemView.context, "Long press, deleting $selectedItem", Toast.LENGTH_SHORT).show()

                return@setOnLongClickListener true
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate a layout from our XML (row_item.XML) and return the holder

        Log.d(TAG, "onCreateViewHolder: ${count++}")

        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    // Replace/bind the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentItem = contacts[position]
        holder.personName.text = currentItem.name
        holder.personAge.text = "Age = ${currentItem.age}"

        //holder.personImage.setImageResource(currentItem.profileImage)

        // Get the context
        val context = holder.itemView.context

        // More info about Glide library https://bumptech.github.io/glide/doc/getting-started.html
        // Make sure to add Internet Permissions to Your Manifest.xml file:
        // The URL must be https since Android does not allow your app to connect http (not secure)
        Glide.with(context)
            .load(currentItem.profileImage)
            .into(holder.personImage)

        //Log.d(TAG, "onBindViewHolder: $position")
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return contacts.size
    }

}
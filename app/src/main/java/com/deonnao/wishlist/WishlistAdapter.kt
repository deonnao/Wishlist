package com.deonnao.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


/**
 * A bridge that tells the recycler view how to display the data we give it
 */
class WishlistAdapter(val listOfItems: List<Wish>, val longClickListener: onLongClickListener) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    interface  onLongClickListener {
        fun onItemLongClicked(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.wish_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get the data model based on position
        val item = listOfItems.get(position)
        holder.item.text = item.item
        holder.price.text = item.price
        holder.url.text = item.url
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store references to elements in our layout view
        val item : TextView
        val price : TextView
        val url : TextView

        //We also create a constructor that accepts the entire item row
        //and does the view lookups to find each sub-view
        init {
            item = itemView.findViewById(R.id.tvItem)
            price = itemView.findViewById(R.id.tvPrice)
            url = itemView.findViewById(R.id.tvURL)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}
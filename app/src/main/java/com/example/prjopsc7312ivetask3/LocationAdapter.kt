package com.example.prjopsc7312ivetask3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LocationAdapter: ListAdapter<Location, LocationAdapter.LocationAdapter>(LocationViewHolder())
{
    class LocationAdapter(view : View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter {
        val inflater = LayoutInflater.from(parent.context)
        return com.example.prjopsc7312ivetask3.LocationAdapter.LocationAdapter(inflater.inflate(
            R.layout.view_layout,parent,false))
    }

    override fun onBindViewHolder(holder: LocationAdapter, position: Int) {
        val local = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.txtLat).text = "Lat: " + local.Lat
        holder.itemView.findViewById<TextView>(R.id.txtLong).text = "Long: " +local.Long
        holder.itemView.findViewById<TextView>(R.id.txtTime).text = "TimeStamp: " +local.TimeStamp
        holder.itemView.findViewById<TextView>(R.id.txtAddress).text = "Address: " +local.Address
    }

}

class LocationViewHolder : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.Address == newItem.Address
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return areItemsTheSame(oldItem,newItem)
    }

}

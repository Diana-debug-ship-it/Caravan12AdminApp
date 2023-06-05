package com.caravan12.admin.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.R
import com.caravan12.admin.app.data_classes.EventInfo
import com.google.firebase.firestore.FirebaseFirestore

class RVEventsAdapter (private val eventsList: ArrayList<EventInfo>) : RecyclerView.Adapter<RVEventsAdapter.RViewHolder>() {

    private lateinit var db: FirebaseFirestore

    class RViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        var deleteEvent: TextView = itemView.findViewById(R.id.deleteEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_events, parent, false)
        return RViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        val currentItem = eventsList[position]

        holder.title.text = currentItem.title
        holder.description.text = currentItem.description

        holder.deleteEvent.setOnClickListener{
            db = FirebaseFirestore.getInstance()
            eventsList.get(position).id?.let { it1 -> db.collection("events").document(it1).delete()
                .addOnSuccessListener {
                    eventsList.remove(eventsList[position])
                    notifyDataSetChanged()
                }}
        }
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

}
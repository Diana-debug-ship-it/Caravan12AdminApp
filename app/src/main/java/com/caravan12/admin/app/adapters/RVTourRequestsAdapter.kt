package com.caravan12.admin.app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.R
import com.caravan12.admin.app.data_classes.TourRequestInfo
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RVTourRequestsAdapter(private val requestsList: ArrayList<TourRequestInfo>): RecyclerView.Adapter<RVTourRequestsAdapter.RViewHolder>() {
    class RViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var applicantEmail: TextView = itemView.findViewById(R.id.tvApplicantEmail)
        var from: TextView = itemView.findViewById(R.id.tvFrom)
        var where: TextView = itemView.findViewById(R.id.tvWhere)
        var dateOfDeparture: TextView = itemView.findViewById(R.id.tvDateOfDeparture)
        var howManyPeople: TextView = itemView.findViewById(R.id.tvPeople)
        var nights: TextView = itemView.findViewById(R.id.tvNights)
        var comments: TextView = itemView.findViewById(R.id.tvComments)

        var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.expandedLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_tour_request, parent, false)
        return RViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        val currentItem = requestsList[position]

        holder.applicantEmail.text = currentItem.applicantEmail
        holder.from.text = currentItem.from
        holder.where.text = currentItem.where
        holder.dateOfDeparture.text = currentItem.dateOfDeparture
        holder.howManyPeople.text = currentItem.howManyPeople
        holder.nights.text = currentItem.nights
        holder.comments.text = currentItem.comments

        val isVisible: Boolean = currentItem.visibility
        holder.constraintLayout.visibility = if(isVisible) View.VISIBLE else View.GONE

        holder.applicantEmail.setOnClickListener{
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return requestsList.size
    }

}
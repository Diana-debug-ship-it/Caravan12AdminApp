package com.caravan12.admin.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.R
import com.caravan12.admin.app.data_classes.TourRequestInfo
import com.google.firebase.firestore.FirebaseFirestore

class RVTourRequestsAdapter(private val requestsList: ArrayList<TourRequestInfo>): RecyclerView.Adapter<RVTourRequestsAdapter.RViewHolder>() {

    private lateinit var db: FirebaseFirestore

    class RViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var applicantEmail: TextView = itemView.findViewById(R.id.tvApplicantEmail)
        var from: TextView = itemView.findViewById(R.id.tvFrom)
        var where: TextView = itemView.findViewById(R.id.tvWhere)
        var dateOfDeparture: TextView = itemView.findViewById(R.id.tvDateOfDeparture)
        var howManyPeople: TextView = itemView.findViewById(R.id.tvPeople)
        var nights: TextView = itemView.findViewById(R.id.tvNights)
        var comments: TextView = itemView.findViewById(R.id.tvComments)
        var children: TextView = itemView.findViewById(R.id.tvChildren)
        var meals: TextView = itemView.findViewById(R.id.tvMeals)
        var rating: TextView = itemView.findViewById(R.id.tvRating)

        var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.expandedLayout)
        var deleteApplication: TextView = itemView.findViewById(R.id.tvDeleteApplication)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_tour_request, parent, false)
        return RViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RViewHolder, position: Int) {
        val currentItem = requestsList[position]

        holder.applicantEmail.text = currentItem.email
        holder.from.text = currentItem.from
        holder.where.text = currentItem.destination
        holder.dateOfDeparture.text = currentItem.dateOfDeparture
        holder.howManyPeople.text = currentItem.adults
        holder.nights.text = currentItem.nights
        holder.comments.text = currentItem.comments
        holder.children.text = currentItem.children
        holder.meals.text = currentItem.meals
        holder.rating.text = currentItem.rating

        val isVisible: Boolean = currentItem.visibility
        holder.constraintLayout.visibility = if(isVisible) View.VISIBLE else View.GONE

        holder.applicantEmail.setOnClickListener{
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }


        holder.deleteApplication.setOnClickListener{
            db = FirebaseFirestore.getInstance()
            requestsList.get(position).id?.let { it1 -> db.collection("tourRequests").document(it1).delete()
                .addOnSuccessListener {
                    requestsList.remove(requestsList[position])
                    notifyDataSetChanged()
                }}
        }

    }

    override fun getItemCount(): Int {
        return requestsList.size
    }

}
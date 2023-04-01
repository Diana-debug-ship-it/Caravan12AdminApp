package com.caravan12.admin.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.R
import com.caravan12.admin.app.data_classes.UserInfo

class RVUsersAdapter(private val usersList: ArrayList<UserInfo>): RecyclerView.Adapter<RVUsersAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var email: TextView = itemView.findViewById(R.id.textApplicantEmail)
        var name: TextView = itemView.findViewById(R.id.tvUserName)
        var number: TextView = itemView.findViewById(R.id.tvUserNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_user, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = usersList[position]

        holder.email.text = currentItem.email
        holder.name.text = currentItem.name
        holder.number.text = currentItem.number
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}
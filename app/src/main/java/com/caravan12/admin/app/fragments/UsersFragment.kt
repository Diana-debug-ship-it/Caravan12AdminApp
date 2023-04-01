package com.caravan12.admin.app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.adapters.RVUsersAdapter
import com.caravan12.admin.app.data_classes.UserInfo
import com.caravan12.admin.app.databinding.FragmentUsersBinding
import com.google.firebase.database.*

class UsersFragment : Fragment() {

    private lateinit var adapter: RVUsersAdapter
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var usersArray: ArrayList<UserInfo>

    private lateinit var database: DatabaseReference

    private lateinit var binding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersArray = arrayListOf<UserInfo>()
        getData()
        setData()
    }

    private fun setData() {
        val layoutManager = LinearLayoutManager(context)
        recyclerViewUsers = binding.recyclerViewUsers
        recyclerViewUsers.layoutManager = layoutManager
        recyclerViewUsers.setHasFixedSize(false)

        adapter = RVUsersAdapter(usersArray)
        recyclerViewUsers.adapter = adapter
    }

    private fun getData() {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UserInfo::class.java)
                        usersArray.add(user!!)
                    }
                    recyclerViewUsers.adapter = RVUsersAdapter(usersArray)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UsersFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
package com.caravan12.admin.app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.R
import com.caravan12.admin.app.adapters.RVUsersAdapter
import com.caravan12.admin.app.data_classes.TourRequestInfo
import com.caravan12.admin.app.data_classes.UserInfo
import com.caravan12.admin.app.databinding.FragmentUsersBinding
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import io.github.muddz.styleabletoast.StyleableToast

class UsersFragment : Fragment() {

    private lateinit var adapter: RVUsersAdapter
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var usersArray: ArrayList<UserInfo>

    private lateinit var database: DatabaseReference
    private lateinit var db: FirebaseFirestore

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
        db = FirebaseFirestore.getInstance()
        db.collection("users").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val user: UserInfo? = data.toObject(UserInfo::class.java)
                        if (user!=null) {
                            usersArray.add(user)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Ошибка при получении данных", Toast.LENGTH_SHORT).show()
            }
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
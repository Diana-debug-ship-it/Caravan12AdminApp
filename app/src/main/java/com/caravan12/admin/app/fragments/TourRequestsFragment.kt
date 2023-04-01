package com.caravan12.admin.app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.adapters.RVTourRequestsAdapter
import com.caravan12.admin.app.data_classes.TourRequestInfo
import com.caravan12.admin.app.databinding.FragmentTourRequestsBinding
import com.google.firebase.database.*

class TourRequestsFragment : Fragment() {

    private lateinit var adapter: RVTourRequestsAdapter
    private lateinit var recyclerViewRequests: RecyclerView
    private lateinit var requestsArray: ArrayList<TourRequestInfo>

    private lateinit var database: DatabaseReference
    private lateinit var query: Query

    private lateinit var binding: FragmentTourRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTourRequestsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestsArray = arrayListOf<TourRequestInfo>()
        getData()
        setData()
    }

    private fun getData() {
        database = FirebaseDatabase.getInstance().getReference("tourRequests")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (requestSnapshot in snapshot.children) {
                        val request = requestSnapshot.getValue(TourRequestInfo::class.java)
                        requestsArray.add(request!!)
                    }
                    recyclerViewRequests.adapter = RVTourRequestsAdapter(requestsArray)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun deleteFromDatabase(i: Int) {
        database = FirebaseDatabase.getInstance().reference
        query = database.child("tourRequests").orderByChild("applicantEmail")
    }

    private fun setData() {
        val layoutManager = LinearLayoutManager(context)
        recyclerViewRequests = binding.recyclerViewRequests
        recyclerViewRequests.layoutManager = layoutManager
        recyclerViewRequests.setHasFixedSize(false)

        adapter = RVTourRequestsAdapter(requestsArray)
        recyclerViewRequests.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TourRequestsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
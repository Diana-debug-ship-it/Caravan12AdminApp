package com.caravan12.admin.app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.R
import com.caravan12.admin.app.adapters.RVTourRequestsAdapter
import com.caravan12.admin.app.data_classes.TourRequestInfo
import com.caravan12.admin.app.databinding.FragmentTourRequestsBinding
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import io.github.muddz.styleabletoast.StyleableToast

class TourRequestsFragment : Fragment() {

    private lateinit var adapter: RVTourRequestsAdapter
    private lateinit var recyclerViewRequests: RecyclerView
    private lateinit var requestsArray: ArrayList<TourRequestInfo>

    private lateinit var db: FirebaseFirestore

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

        db = FirebaseFirestore.getInstance()
        db.collection("tourRequests").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val documentId: String = data.id
                        val request: TourRequestInfo? = data.toObject(TourRequestInfo::class.java)
                        if (request!=null) {
                            request.id = documentId
                            requestsArray.add(request)
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Ошибка при получении данных", Toast.LENGTH_SHORT).show()
            }
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
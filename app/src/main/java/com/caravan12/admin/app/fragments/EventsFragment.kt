package com.caravan12.admin.app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.admin.app.activities.MainActivity
import com.caravan12.admin.app.adapters.RVEventsAdapter
import com.caravan12.admin.app.data_classes.EventInfo
import com.caravan12.admin.app.databinding.FragmentEventsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding

    private lateinit var db: FirebaseFirestore

    private lateinit var adapter: RVEventsAdapter
    private lateinit var recyclerViewEvents: RecyclerView
    private lateinit var eventsArray: ArrayList<EventInfo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEventsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsArray = arrayListOf<EventInfo>()
        binding.apply {
            buttonAddEvent.setOnClickListener {
                addDataToDatabase()
            }
        }
        getData()
        setData()
    }

    private fun getData() {
        db = FirebaseFirestore.getInstance()
        db.collection("events").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val documentId: String = data.id
                        val event: EventInfo? = data.toObject(EventInfo::class.java)
                        if (event != null) {
                            event.id = documentId
                            eventsArray.add(event)
                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Ошибка при получении данных", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun setData() {
        val layoutManager = LinearLayoutManager(context)
        recyclerViewEvents = binding.recyclerViewEvents
        recyclerViewEvents.layoutManager = layoutManager
        recyclerViewEvents.setHasFixedSize(false)

        adapter = RVEventsAdapter(eventsArray)
        recyclerViewEvents.adapter = adapter
    }

    private fun addDataToDatabase() {

        val title = binding.ediTextTitle.text.toString()
        val description = binding.ediTextDescription.text.toString()

        if (title.isEmpty() || description.isEmpty()) {
            if (title.isEmpty()) {
                binding.ediTextTitle.error = "Поле не может быть пустым"
            }
            if (description.isEmpty()) {
                binding.ediTextDescription.error = "Поле не может быть пустым"
            }
        } else {
            val event = hashMapOf(
                "title" to title,
                "description" to description
            )
            db.collection("events").add(event)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Акция опубликована",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.ediTextTitle.setText("")
                    binding.ediTextDescription.setText("")
                    eventsArray.clear()
                    getData()
                    setData()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Произошла ошибка", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    companion object {
        fun newInstance() =
            EventsFragment().apply {
            }
    }
}
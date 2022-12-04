package com.sanyamjain.juet_outpass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.daos.PostDao
import com.sanyamjain.juet_outpass.models.Post
import kotlinx.android.synthetic.main.activity_previous_application.*

class PreviousApplicationActivity : AppCompatActivity() {
    private lateinit var adapter: PreviousApplicationAdapter
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_application)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        auth=Firebase.auth
        val query=db.collection("post").whereEqualTo("userID",auth.currentUser!!.uid)
            .whereNotEqualTo("status","Applied")
        val recyclerViewOptions=FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
        adapter= PreviousApplicationAdapter(recyclerViewOptions)

        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
package com.sanyamjain.juet_outpass

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.daos.UserDao
import com.sanyamjain.juet_outpass.models.Post
import com.sanyamjain.juet_outpass.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class WardenCurrentAdapter(options: FirestoreRecyclerOptions<Post>,val listener:IPostAdapter) :
    FirestoreRecyclerAdapter<Post, WardenCurrentAdapter.PostViewHolder>(
        options
    ) {
    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView =itemView.findViewById(R.id.NameValue)
        val enroll: TextView =itemView.findViewById(R.id.enrollValue)
        val from: TextView =itemView.findViewById(R.id.fromValue)
        val to: TextView =itemView.findViewById(R.id.toValue)
        val purpose: TextView =itemView.findViewById(R.id.purposeValue)
        val placeToVisit: TextView =itemView.findViewById(R.id.placeToVisitValue)
        val approve:Button=itemView.findViewById(R.id.approve)
        val reject:Button=itemView.findViewById(R.id.reject)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(model.userID)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val u = document.toObject<User>()
                    holder.name.text=u!!.displayName
                    holder.enroll.text=u!!.email.subSequence(0,7)
                } else {
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        holder.from.text=model.from
        holder.to.text=model.to
        holder.placeToVisit.text=model.placeOnLeave
        holder.purpose.text=model.purpose
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder=PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.warden_current_layout,parent,false))
        viewHolder.reject.setOnClickListener {
            listener.onRejectClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        viewHolder.approve.setOnClickListener {
            listener.onApprovedClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }
}
interface IPostAdapter {
    fun onRejectClicked(postId: String)
    fun onApprovedClicked(postId: String)
}
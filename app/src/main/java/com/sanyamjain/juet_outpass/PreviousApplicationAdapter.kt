package com.sanyamjain.juet_outpass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.sanyamjain.juet_outpass.models.Post

class PreviousApplicationAdapter(options: FirestoreRecyclerOptions<Post>) :FirestoreRecyclerAdapter<Post, PreviousApplicationAdapter.PostViewHolder>(
    options
) {

    class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val from:TextView=itemView.findViewById(R.id.fromValue)
        val to:TextView=itemView.findViewById(R.id.toValue)
        val purpose:TextView=itemView.findViewById(R.id.purposeValue)
        val placeToVisit:TextView=itemView.findViewById(R.id.placeToVisitValue)
        val status:TextView=itemView.findViewById(R.id.statusValue)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.from.text=model.from
        holder.to.text=model.to
        holder.placeToVisit.text=model.placeOnLeave
        holder.purpose.text=model.purpose
        holder.status.text=model.status
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.previous_application_layout,parent,false))
    }
}
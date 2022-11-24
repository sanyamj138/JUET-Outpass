package com.sanyamjain.juet_outpass.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.sanyamjain.juet_outpass.models.Post
import com.sanyamjain.juet_outpass.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostDao {
    private val db = FirebaseFirestore.getInstance()
    private val postCollection = db.collection("post")

    fun addPost(post: Post?) {
        post?.let {
            GlobalScope.launch(Dispatchers.IO) {
                postCollection.document().set(it)
            }
        }
    }

    fun getPostById(uId: String): Task<DocumentSnapshot> {
        return postCollection.document(uId).get()
    }
}
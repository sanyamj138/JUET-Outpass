package com.sanyamjain.juet_outpass.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sanyamjain.juet_outpass.models.Post
import com.sanyamjain.juet_outpass.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    private val db = FirebaseFirestore.getInstance()
    private val postCollection = db.collection("post")
    private lateinit var auth: FirebaseAuth

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
    fun update(postId: String,b: Boolean) {
        GlobalScope.launch {
            auth= Firebase.auth
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            if(b)
            post.status="Approved by Warden"
            else
                post.status="Rejected by Warden"
            postCollection.document(postId).set(post)
        }

    }
    fun updateSupre(postId: String,b: Boolean) {
        GlobalScope.launch {
            auth= Firebase.auth
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            if(b)
                post.status="Approved by Supre"
            else
                post.status="Rejected by Supre"
            postCollection.document(postId).set(post)
        }

    }

    fun updateParent(postId: String,b: Boolean) {
        GlobalScope.launch {
            auth= Firebase.auth
            val currentUserId = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Post::class.java)!!
            if(b)
                post.status="Approved by Parents"
            else
                post.status="Rejected by Parents"
            postCollection.document(postId).set(post)
        }

    }
}
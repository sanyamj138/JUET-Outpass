package com.sanyamjain.juet_outpass.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.sanyamjain.juet_outpass.models.User
import com.sanyamjain.juet_outpass.models.Warden
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WardenDao {
    private val db = FirebaseFirestore.getInstance()
    private val wardenCollection = db.collection("wardens")

    fun addWarden(warden: Warden?) {
        warden?.let {
            GlobalScope.launch(Dispatchers.IO) {
                wardenCollection.document(warden.uid).set(it)
            }
        }
    }

    fun getWardenById(uId: String): Task<DocumentSnapshot> {
        return wardenCollection.document(uId).get()
    }
}
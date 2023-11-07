package com.example.emptyactivity.DataModels

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class PostRepositoryFirestore(val test: FirebaseFirestore){
    val db = Firebase.firestore
}
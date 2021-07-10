package com.example.mystuff.services

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DB {

    companion object {

        val firestore
            get():CollectionReference = Firebase.firestore.collection("mystuff")

    }

}
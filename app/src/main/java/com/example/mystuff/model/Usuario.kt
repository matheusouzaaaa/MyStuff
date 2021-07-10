package com.example.mystuff.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Usuario(
    val nome:String? = null,
    val criacao:Timestamp? = null,
    @DocumentId
    val id:String? = null
)
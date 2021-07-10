package com.example.mystuff.model

import com.google.firebase.firestore.DocumentId

data class Comodo (
    val nome:String? = null,
    @DocumentId
    val id:String? = null,
)

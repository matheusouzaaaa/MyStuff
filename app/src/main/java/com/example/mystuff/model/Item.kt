package com.example.mystuff.model

import com.google.firebase.firestore.DocumentId

data class Item (
    val nome:String? = null,
    val quantidade:Double? = null,
    val comodos:Map<String, Double>? = null,
    @DocumentId
    var id:String? = null
)
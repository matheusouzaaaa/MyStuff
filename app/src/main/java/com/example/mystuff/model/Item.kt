package com.example.mystuff.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

data class Item (
    val nome:String? = null,
    val quantidadeTotal:Double? = null,
    val comodos:ArrayList<DocumentReference>? = null,
    val quantidades: ArrayList<Int>? = null,
    @DocumentId
    var id:String? = null
)
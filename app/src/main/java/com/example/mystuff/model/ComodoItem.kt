package com.example.mystuff.model

import com.google.firebase.firestore.DocumentId

data class ComodoItem ( val comodo:String? = null, val item:String? = null, val quantidade:Int? = null, @DocumentId val id:String? = null )
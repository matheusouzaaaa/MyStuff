package com.example.mystuff.model

import com.google.firebase.firestore.DocumentId

data class Item( var nome:String? = null, var quantidade:Int? = null, @DocumentId var id:String? = null )
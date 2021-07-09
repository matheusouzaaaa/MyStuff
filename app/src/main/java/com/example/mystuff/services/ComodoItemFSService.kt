package com.example.mystuff.services

import com.example.mystuff.model.ComodoItem
import com.example.mystuff.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.QuerySnapshot

class ComodoItemFSService ( val usuario: Usuario ): DataService<ComodoItem> {

    override val db = DBService.getFirestoreDB().document(usuario.id!!)
    val listaComodoItem = db.collection("comodoItem")

    /**
     * Retorna todos os itens salvos na coleção
     */
    override fun retornarTodos():Task<QuerySnapshot> = listaComodoItem.get()

    /**
     * Retorna um único item, através de seu ID
     */
    override fun retornarUm( id:String ):Task<DocumentSnapshot> = listaComodoItem.document(id).get()

    /**
     * Cria um novo item na coleção
     */
    override fun novo( obj:ComodoItem ):Task<DocumentReference> = listaComodoItem.add(hashMapOf( "comodo" to obj.comodo, "item" to obj.item, "quantidade" to obj.quantidade ))

    /**
     * Atualiza um item na coleção
     */
    override fun atualizar( obj:ComodoItem ):Task<Void> = listaComodoItem.document(obj.id!!).update(mapOf("comodo" to obj.comodo, "item" to obj.item, "quantidade" to obj.quantidade))

    /**
     * Deleta um item da coleção
     */
    override fun deletar( id:String ):Task<Void> = listaComodoItem.document(id).delete()

    fun incrementar( itemAtual:DocumentReference, quantidade:Int ):Task<Void> = itemAtual.update("quantidade", FieldValue.increment(quantidade.toDouble()))
}
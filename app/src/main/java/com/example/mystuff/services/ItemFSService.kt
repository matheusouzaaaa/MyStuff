package com.example.mystuff.services

import com.example.mystuff.model.Item
import com.example.mystuff.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

class ItemFSService( val usuario: Usuario ) : DataService<Item> {

    override val db = DBService.getFirestoreDB().document(usuario.id!!)
    private val itens = db.collection("itens")

    /**
     * Retorna todos os itens salvos na coleção
     */
    override fun retornarTodos():Task<QuerySnapshot> = itens.get()

    /**
     * Retorna um único item, através de seu ID
     */
    override fun retornarUm( id:String ):DocumentReference = itens.document(id)

    /**
     * Cria um novo item na coleção
     */
    override fun novo( obj:Item ):Task<DocumentReference> = itens.add(hashMapOf("nome" to obj.nome, "quantidade" to obj.quantidade))

    /**
     * Atualiza um item na coleção
     */
    override fun atualizar( obj:Item ):Task<Void> = itens.document(obj.id!!).update(mapOf("nome" to obj.nome, "quantidade" to obj.quantidade))

    /**
     * Deleta um item da coleção
     */
    override fun deletar( id:String ):Task<Void> = itens.document(id).delete()

}
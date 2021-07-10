package com.example.mystuff.services

import com.example.mystuff.model.Comodo
import com.example.mystuff.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class ComodoFSService( usuario: Usuario ):DataService<Comodo> {

    val colecaoComodos = DBService.getFirestoreDB().document(usuario.id!!).collection("comodos")

    /**
     * Retorna todos os itens salvos na coleção
     */
    override fun retornarTodos():Task<QuerySnapshot> = colecaoComodos.get()

    /**
     * Retorna um único item, através de seu ID
     */
    override fun retornarUm( id:String ):Task<DocumentSnapshot> = colecaoComodos.document(id).get()

    /**
     * Cria um novo item na coleção
     */
    override fun novo( obj:Comodo ):Task<DocumentReference> = colecaoComodos.add(obj)

    /**
     * Atualiza um item na coleção
     */
    override fun atualizar( obj:Comodo ):Task<Void> = colecaoComodos.document(obj.id!!).update("nome", obj.nome )

    /**
     * Deleta um item da coleção
     */
    override fun deletar( id:String ):Task<Void> = colecaoComodos.document(id).delete()


}
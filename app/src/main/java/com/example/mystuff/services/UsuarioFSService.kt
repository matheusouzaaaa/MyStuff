package com.example.mystuff.services

import com.example.mystuff.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

class UsuarioFSService: DataService<Usuario> {

    val usuarios = DBService.getFirestoreDB()

    /**
     * Retorna um único item, através de seu ID
     */
    override fun retornarUm( id:String ):Task<DocumentSnapshot> = usuarios.document(id).get()

    /**
     * Cria um novo item
     */
    override fun novo( obj:Usuario ) = usuarios.document(obj.id!!).set(hashMapOf("nome" to obj.nome, "criacao" to Timestamp.now()))

    /**
     * Atualiza um item
     */
    override fun atualizar( obj:Usuario ): Any? {
        TODO("Not yet implemented")
    }

    /**
     * Deleta um item da coleção
     */
    override fun deletar( id:String ): Any? {
        TODO("Not yet implemented")
    }

    /**
     * Retorna todos os itens
     */
    override fun retornarTodos(): Any? {
        TODO("Not yet implemented")
    }


}
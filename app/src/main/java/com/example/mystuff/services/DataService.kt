package com.example.mystuff.services

import com.google.firebase.firestore.DocumentReference

interface DataService<T> {

    val db: DocumentReference

    /**
     * Retorna todos os itens
     */
    fun retornarTodos(): Any?

    /**
     * Retorna um único item, através de seu ID
     */
    fun retornarUm( id:String ): Any?

    /**
     * Cria um novo item
     */
    fun novo( obj:T ): Any?

    /**
     * Atualiza um item
     */
    fun atualizar( obj:T ): Any?

    /**
     * Deleta um item da coleção
     */
    fun deletar( id:String ): Any?

}
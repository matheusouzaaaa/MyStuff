package com.example.mystuff.services

interface DataService<T> {

    /**
     * Retorna todos os itens
     */
    fun retornarTodos():Any?

    /**
     * Retorna um único item, através de seu ID
     */
    fun retornarUm( id:String ):Any?

    /**
     * Cria um novo item
     */
    fun novo( obj:T ):Any?

    /**
     * Atualiza um item
     */
    fun atualizar( obj:T ):Any?

    /**
     * Deleta um item da coleção
     */
    fun deletar( id:String ):Any?

}
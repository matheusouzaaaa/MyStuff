package com.example.mystuff.services

import com.example.mystuff.model.Comodo
import com.example.mystuff.model.ComodoItem
import com.example.mystuff.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class ComodoFSService(val usuario: Usuario ) : DataService<Comodo> {

    override val db = DBService.getFirestoreDB().document(usuario.id!!)
    val comodos = db.collection("comodos")

    /**
     * Retorna todos os itens salvos na coleção
     */
    override fun retornarTodos():Task<QuerySnapshot> = comodos.get()

    /**
     * Retorna um único item, através de seu ID
     */
    override fun retornarUm( id:String ):Task<DocumentSnapshot> = comodos.document(id).get()

    /**
     * Cria um novo item na coleção
     */
    override fun novo( obj:Comodo ):Task<DocumentReference> = comodos.add(hashMapOf( "nome" to obj.nome ))

    /**
     * Atualiza um item na coleção
     */
    override fun atualizar( obj:Comodo ):Task<Void> = comodos.document(obj.id!!).update(mapOf( "nome" to obj.nome ))

    /**
     * Deleta um item da coleção
     */
    override fun deletar( id:String ):Task<Void> = comodos.document(id).delete()

    fun adicionarItem( idComodo:String, idItem:String, quantidade:Int ) {

        // Separar métodos - Busca e incremento/adição

        // Verificar se já há o item
        val comodoItemFSService = ComodoItemFSService(usuario)

        comodoItemFSService.listaComodoItem
            .whereEqualTo("comodo", idComodo)
            .whereEqualTo("item", idItem).get()
                .addOnSuccessListener {

                    if( it.size() != 0 ){

                        val itemAtual = it.documents[0].reference
                        comodoItemFSService.incrementar(itemAtual, quantidade)
                            .addOnSuccessListener {

                            }
                            .addOnFailureListener {  }

                    } else {

                        comodoItemFSService.novo( ComodoItem( idComodo, idItem, quantidade ) )
                            .addOnSuccessListener {  }
                            .addOnFailureListener {  }

                    }

                }
                .addOnFailureListener {
                    // Erro - busca
                }

    }

}
package com.example.mystuff.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mystuff.R
import com.example.mystuff.services.ComodoFSService
import com.example.mystuff.services.ItemFSService
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.DocumentReference

class InventarioViewModel( application:Application ):AndroidViewModel(application) {

    var usuarioAtual:Usuario? = null

//    lateinit var itemService:ItemFSService
//    lateinit var comodoService:ComodoFSService

    lateinit var itemSelecionado:Item
    lateinit var comodoSelecionado:Comodo

    var ordenacao = Ordenacao.numerica
    var agrupamento = Agrupamento.porComodo

    var acaoString:AcaoString? = null
    var acao:Acao? = null
        set(acaoEnum) {
            field = acaoEnum
            when(acaoEnum){
                Acao.novoItem -> acaoString = AcaoString.novoItem
                Acao.editarItem -> acaoString = AcaoString.editarItem
                Acao.novoComodo -> acaoString = AcaoString.novoComodo
                Acao.editarComodo -> acaoString = AcaoString.editarComodo
                else -> acaoString = null
            }
        }

    fun carregarComodos( comodos:ArrayList<DocumentReference> ):ArrayList<Comodo>? {

        var resultado = ArrayList<Comodo>()

        for ( comodo in comodos){

            comodo.get()
                .addOnSuccessListener {
                    it.toObject(Comodo::class.java)?.let { it1 -> resultado.add(it1) }
                }

        }

        return null

    }

    fun logout():Boolean {

        var resultado = false

        usuarioAtual = null

        AuthUI.getInstance().signOut(getApplication())
            .addOnCompleteListener {
                resultado = true
            }

        return resultado

    }

    class AcaoString( val stringAcao:Int, val stringObjeto:Int ) {
        companion object{
            val novoItem get() = AcaoString( R.string.telaEdicao_novo, R.string.telaEdicao_item )
            val editarItem get() = AcaoString( R.string.telaEdicao_editar, R.string.telaEdicao_item )
            val novoComodo get() = AcaoString( R.string.telaEdicao_novo, R.string.telaEdicao_comodo )
            val editarComodo get() = AcaoString( R.string.telaEdicao_editar, R.string.telaEdicao_comodo )
        }
    }

    enum class Acao {
        novoItem, editarItem, novoComodo, editarComodo
    }

    enum class Ordenacao {
        alfabetica, numerica
    }

    enum class Agrupamento {
        porComodo, porItem
    }

}
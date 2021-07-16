package com.example.mystuff.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mystuff.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.DocumentReference

class InventarioViewModel( application:Application ):AndroidViewModel(application) {

    class AcaoString(val stringAcao:Int, val stringObjeto:Int ) {
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

    var usuarioAtual:Usuario? = null

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


    var itemSelecionado:Item? = null
    var comodoSelecionado:Comodo? = null

    fun carregarComodos( comodos:ArrayList<DocumentReference> ):ArrayList<Comodo>? {
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

}
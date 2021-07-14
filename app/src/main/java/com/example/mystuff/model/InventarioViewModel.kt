package com.example.mystuff.model

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.mystuff.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InventarioViewModel: ViewModel() {

    var usuarioAtual:Usuario? = null

    var itemSelecionado:Item? = null
    var comodoSelecionado:Comodo? = null

    fun logout ( contexto:Context, tela:Fragment ){

        usuarioAtual = null

        AuthUI.getInstance().signOut(contexto)
            .addOnCompleteListener {
                findNavController(tela).navigate(R.id.action_telaPrincipal_to_telaLogin)
            }

    }

}
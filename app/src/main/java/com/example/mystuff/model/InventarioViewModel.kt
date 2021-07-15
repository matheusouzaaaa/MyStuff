package com.example.mystuff.model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.mystuff.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InventarioViewModel(application: Application) : AndroidViewModel(application) {

    var usuarioAtual:Usuario? = null

    var itemSelecionado:Item? = null
    var comodoSelecionado:Comodo? = null

    fun logout ( controlador:NavController ){

        usuarioAtual = null

        AuthUI.getInstance().signOut(getApplication())
            .addOnCompleteListener {
                controlador.navigate(R.id.action_telaPrincipal_to_telaLogin)
            }

    }

}
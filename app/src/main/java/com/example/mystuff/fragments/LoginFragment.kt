package com.example.mystuff.fragments

import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.mystuff.R
import com.example.mystuff.model.InventarioViewModel
import com.example.mystuff.model.Usuario
import com.example.mystuff.services.UsuarioFSService
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private val inventarioViewModel:InventarioViewModel by navGraphViewModels(R.id.loginFragment)

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.resultadoLogin(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = Firebase.auth.currentUser

        if (user != null) {

            // User is signed in


        } else {

            val providers = arrayListOf( AuthUI.IdpConfig.EmailBuilder().build() )

            val loginIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            signInLauncher.launch(loginIntent)

        }

    }

    private fun resultadoLogin( resultado:FirebaseAuthUIAuthenticationResult ) {

        if (resultado.resultCode == RESULT_OK) {

            FirebaseAuth.getInstance().currentUser.let { usuarioFB ->

                val servico = UsuarioFSService()

                servico.retornarUm(usuarioFB!!.uid)
                    .addOnSuccessListener {

                        inventarioViewModel.usuarioAtual = it.toObject(Usuario::class.java)

                        // Navegar para Tela Principal

                    }
                    .addOnFailureListener {

                        servico.novo(Usuario(null, Timestamp.now(), usuarioFB.uid))
                            .addOnSuccessListener {
                                // Navegar para Tela Principal
                            }
                            .addOnFailureListener {
                                // Mensagem de Erro ou alternativa
                            }

                    }

            }

        } else {

            // Falha no cadastro, mensagem de erro

        }

    }

}
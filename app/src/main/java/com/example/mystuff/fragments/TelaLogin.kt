package com.example.mystuff.fragments

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.mystuff.R
import com.example.mystuff.databinding.LoginNoticeBinding
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

class TelaLogin : Fragment() {

    private val inventarioViewModel:InventarioViewModel by navGraphViewModels(R.id.telaPrincipal)
    private var loginCancelado = false

    lateinit var _binding: LoginNoticeBinding
    val binding
        get() = _binding

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.resultadoLogin(res)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {

        _binding = LoginNoticeBinding.inflate(layoutInflater, container, false)

        binding.viewModel = inventarioViewModel
        binding.tela = this

        return binding.root

    }

    fun verificarLogin(){

        if ( Firebase.auth.currentUser != null) {

            Log.i("MyStuff", "1 - Usuário já logado no Firebase Auth")

            voltarParaTelaPrincipal()

        } else {

            Log.i("MyStuff", "2 - usuário não logado, iniciar processo de login")

            val providers = arrayListOf( AuthUI.IdpConfig.EmailBuilder().build() )

            val loginIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            signInLauncher.launch(loginIntent)

        }
    }

    private fun resultadoLogin( resultado:FirebaseAuthUIAuthenticationResult ) {

        Log.i("MyStuff", "3 - Retorno de login ou cadastro")

        if (resultado.resultCode == RESULT_OK) {

            FirebaseAuth.getInstance().currentUser.let { usuarioAuth ->

                val servico = UsuarioFSService()

                servico.retornarUm(usuarioAuth!!.uid)
                    .addOnSuccessListener {

                        it.toObject(Usuario::class.java).let { usuarioFirestore ->

                            if( usuarioFirestore?.id == null ){

                                Log.i("MyStuff", "5 - Novo usuário")

                                servico.novo(Usuario(usuarioAuth.displayName, Timestamp.now(), usuarioAuth.uid))
                                    .addOnSuccessListener {
                                        voltarParaTelaPrincipal()
                                    }
                                    .addOnFailureListener { /*Mensagem de Erro ou alternativa*/ }

                            } else {

                                Log.i("MyStuff", "4 - Usuário já cadastrado no Firestore, $usuarioFirestore")

                                inventarioViewModel.usuarioAtual = usuarioFirestore
                                voltarParaTelaPrincipal()

                            }

                        }


                    }
                    .addOnFailureListener {}

            }

        } else {
            Log.i("MyStuff", "5 - Login Cancelado")
        }

    }

    private fun voltarParaTelaPrincipal(){
        findNavController().popBackStack()
    }

}
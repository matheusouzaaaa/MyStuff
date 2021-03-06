package com.example.mystuff.fragments

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.mystuff.R
import com.example.mystuff.databinding.AvisoLoginBinding
import com.example.mystuff.model.InventarioViewModel
import com.example.mystuff.model.Usuario
import com.example.mystuff.services.ComodoFSService
import com.example.mystuff.services.ItemFSService
import com.example.mystuff.services.UsuarioFSService
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TelaLogin : Fragment() {

    private val inventarioViewModel:InventarioViewModel by activityViewModels()

    lateinit var _binding: AvisoLoginBinding
    val binding
        get() = _binding

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.resultadoLogin(res)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {

        Log.i("MyStuff", "Tela de Login")

        _binding = AvisoLoginBinding.inflate(layoutInflater, container, false)

        binding.viewModel = inventarioViewModel
        binding.avisoLogin = this

        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root

    }

    fun verificarLogin( v:View ){

        val usuarioFB = Firebase.auth.currentUser

        if ( usuarioFB != null) {

            Log.i("MyStuff", "1 - Usu??rio j?? logado no Firebase Auth")

            configurarViewModel(Usuario(usuarioFB.displayName, Timestamp.now(), usuarioFB.uid))
            voltarParaTelaPrincipal()

        } else {

            Log.i("MyStuff", "2 - usu??rio n??o logado, iniciar processo de login")

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

                UsuarioFSService().retornarUm(usuarioAuth!!.uid)
                    .addOnSuccessListener {

                        it.toObject(Usuario::class.java).let { usuarioFirestore ->

                            if( usuarioFirestore?.id == null ){

                                Log.i("MyStuff", "5 - Novo usu??rio")

                                val novoUsuario = Usuario(usuarioAuth.displayName, Timestamp.now(), usuarioAuth.uid)

                                UsuarioFSService().novo(novoUsuario)
                                    .addOnSuccessListener {
                                        configurarViewModel(novoUsuario)
                                        voltarParaTelaPrincipal()
                                    }
                                    .addOnFailureListener {
                                        mostrarSnackErro()
                                    }

                            } else {

                                Log.i("MyStuff", "4 - Usu??rio j?? cadastrado no Firestore, $usuarioFirestore")

                                configurarViewModel(usuarioFirestore)

                                voltarParaTelaPrincipal()

                            }

                        }


                    }
                    .addOnFailureListener {
                        mostrarSnackErro()
                    }

            }

        } else {
            Log.i("MyStuff", "5 - Login Cancelado")
        }

    }

    private fun configurarViewModel( u:Usuario ){
        inventarioViewModel.apply {
            usuarioAtual = u
//            itemService = ItemFSService(usuarioAtual!!)
//            comodoService = ComodoFSService(usuarioAtual!!)
        }
    }

    private fun voltarParaTelaPrincipal(){
        findNavController().popBackStack()
    }

    private fun mostrarSnackErro(){
        Snackbar.make(binding.root, R.string.telaLogin_msgErro, Snackbar.LENGTH_SHORT ).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
    }

}
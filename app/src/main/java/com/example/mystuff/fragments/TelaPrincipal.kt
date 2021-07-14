package com.example.mystuff.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.mystuff.R
import com.example.mystuff.databinding.FragmentTelaPrincipalBinding
import com.example.mystuff.model.InventarioViewModel
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : Fragment() {

    val viewModel:InventarioViewModel by navGraphViewModels(R.id.telaPrincipal)
    lateinit var _binding:FragmentTelaPrincipalBinding
    val binding
        get() = _binding

    override fun onCreateView( inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle? ):View {

        _binding = FragmentTelaPrincipalBinding.inflate(layoutInflater, container, false)

        binding.contexto = requireContext()
        binding.tela = this
        binding.viewmodel = viewModel

        return binding.root

    }

    override fun onViewCreated( view:View, savedInstanceState:Bundle? ) {
        super.onViewCreated(view, savedInstanceState)

        if( FirebaseAuth.getInstance().currentUser == null )
            findNavController().navigate(R.id.action_telaPrincipal_to_telaLogin)
    }

}
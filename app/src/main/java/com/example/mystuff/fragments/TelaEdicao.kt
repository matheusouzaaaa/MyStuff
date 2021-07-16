package com.example.mystuff.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mystuff.databinding.FragmentTelaEdicaoBinding
import com.example.mystuff.model.InventarioViewModel

class TelaEdicao : Fragment() {

    private lateinit var _binding:FragmentTelaEdicaoBinding
    val binding get() = _binding

    val inventarioViewModel:InventarioViewModel by activityViewModels()

    override fun onCreateView( inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle? ):View {

        _binding = FragmentTelaEdicaoBinding.inflate(layoutInflater)

        binding.apply {

            viewModel = inventarioViewModel
            edicao = this@TelaEdicao
            inventarioViewModel.acaoString?.let {
                stringAcao = getString(it.stringAcao)
                stringObjeto = getString(it.stringObjeto)
            }

            when(inventarioViewModel.acao){
                InventarioViewModel.Acao.novoItem-> {
                    btnDeletar.visibility = View.GONE
                }
                InventarioViewModel.Acao.novoComodo -> {
                    txtiLayoutQuantidade.visibility = View.GONE
                    spnComodo.visibility = View.GONE
                    txtiLayoutQuantidadeComodo.visibility = View.GONE
                    btnDeletar.visibility = View.GONE
                }
                InventarioViewModel.Acao.editarComodo -> {
                    txtiLayoutQuantidade.visibility = View.GONE
                    spnComodo.visibility = View.GONE
                    txtiLayoutQuantidadeComodo.visibility = View.GONE
                }
            }

        }

        return binding.root
    }

    fun cancelar( v:View ){

        inventarioViewModel.apply {

            acaoString = null
            itemSelecionado = null
            comodoSelecionado = null

        }

        findNavController().popBackStack()

    }

}
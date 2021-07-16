package com.example.mystuff.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.mystuff.R
import com.example.mystuff.databinding.FragmentTelaPrincipalBinding
import com.example.mystuff.model.InventarioViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : Fragment(), PopupMenu.OnMenuItemClickListener {

    val viewModel:InventarioViewModel by activityViewModels()
    lateinit var _binding:FragmentTelaPrincipalBinding
    val binding
        get() = _binding

    override fun onCreateView( inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle? ):View {

        _binding = FragmentTelaPrincipalBinding.inflate(layoutInflater, container, false)

        binding.apply {

            viewmodel = viewModel
            fabNovo.setOnClickListener {
                mostrarMenu(it)
            }

        }

        return binding.root

    }

    override fun onViewCreated( view:View, savedInstanceState:Bundle? ) {
        super.onViewCreated(view, savedInstanceState)

        if( FirebaseAuth.getInstance().currentUser == null )
            findNavController().navigate(R.id.action_telaPrincipal_to_telaLogin)

        (activity as AppCompatActivity).supportActionBar?.show()

    }

    private fun navegarParaEdicao(){
        binding.fabNovo.hide()
        findNavController().navigate(R.id.action_telaPrincipal_to_telaEdicao)
    }

    private fun mostrarMenu( v:View ) {
        PopupMenu(this.requireContext(), v).apply {
            // MainActivity implements OnMenuItemClickListener
            setOnMenuItemClickListener(this@TelaPrincipal)
            inflate(R.menu.novo_opcoes)
            show()
        }
    }

    /**
     * This method will be invoked when a menu item is clicked if the item
     * itself did not already handle the event.
     *
     * @param item the menu item that was clicked
     * @return `true` if the event was handled, `false`
     * otherwise
     */
    override fun onMenuItemClick( item:MenuItem ):Boolean {
        return when (item.itemId) {
            R.id.acao_novo_item -> {
                viewModel.acao = InventarioViewModel.Acao.novoItem
                navegarParaEdicao()
                true
            }
            R.id.acao_novo_comodo -> {
                viewModel.acao = InventarioViewModel.Acao.novoComodo
                navegarParaEdicao()
                true
            }
            else -> false
        }
    }

}
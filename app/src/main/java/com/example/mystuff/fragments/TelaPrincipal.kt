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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystuff.R
import com.example.mystuff.databinding.FragmentTelaPrincipalBinding
import com.example.mystuff.model.FlexibleItem
import com.example.mystuff.model.InventarioViewModel
import com.example.mystuff.model.Item
import com.example.mystuff.model.Usuario
import com.example.mystuff.services.ComodoFSService
import com.example.mystuff.services.ItemFSService
import com.google.firebase.auth.FirebaseAuth
import eu.davidea.flexibleadapter.FlexibleAdapter

class TelaPrincipal : Fragment(), PopupMenu.OnMenuItemClickListener {

    val inventarioViewModel:InventarioViewModel by activityViewModels()
    lateinit var _binding:FragmentTelaPrincipalBinding
    val binding
        get() = _binding

    override fun onCreateView( inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle? ):View {

        _binding = FragmentTelaPrincipalBinding.inflate(layoutInflater, container, false)

        if( FirebaseAuth.getInstance().currentUser == null )
            findNavController().navigate(R.id.action_telaPrincipal_to_telaLogin)
        else
            inventarioViewModel.apply {
                usuarioAtual = Usuario(FirebaseAuth.getInstance().currentUser?.displayName, null, FirebaseAuth.getInstance().currentUser?.uid)
//                itemService = ItemFSService(usuarioAtual!!)
//                comodoService = ComodoFSService(usuarioAtual!!)
            }

        return binding.root

    }

    override fun onViewCreated( view:View, savedInstanceState:Bundle? ) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            viewmodel = inventarioViewModel
            fabNovo.setOnClickListener {
                mostrarMenu(it)
            }

            when(inventarioViewModel.ordenacao){
                InventarioViewModel.Ordenacao.alfabetica -> rbtnOrdenacaoAlfabetica.isChecked = true
                InventarioViewModel.Ordenacao.numerica -> rbtnOrdenacaoNumerica.isChecked = true
            }

            /*
            inventarioViewModel.itemService.retornarTodos()
                .addOnSuccessListener {

                    var itens = ArrayList<FlexibleItem>()

                    it.toObjects(Item::class.java).forEach {
                        itens.add(FlexibleItem(it))
                    }

                    rclListaPrincipal.apply {
                        layoutManager = LinearLayoutManager(this@TelaPrincipal.requireContext())
                        setHasFixedSize(true)
                        adapter = FlexibleAdapter(itens)
                    }

                }
                */

        }

        (activity as AppCompatActivity).supportActionBar?.show()

    }

    fun configurarAgrupamento( v:View ){
        if ((v as SwitchCompat).isChecked)
            inventarioViewModel.agrupamento = InventarioViewModel.Agrupamento.porComodo
        else
            inventarioViewModel.agrupamento = InventarioViewModel.Agrupamento.porItem
    }

    fun configurarOrdenacao( v:View ){
        when(v.id){
            R.id.rbtnOrdenacaoAlfabetica -> inventarioViewModel.ordenacao = InventarioViewModel.Ordenacao.alfabetica
            R.id.rbtnOrdenacaoNumerica -> inventarioViewModel.ordenacao = InventarioViewModel.Ordenacao.numerica
        }
    }

    private fun mostrarMenu( v:View ) {
        PopupMenu(this.requireContext(), v).apply {
            // MainActivity implements OnMenuItemClickListener
            setOnMenuItemClickListener(this@TelaPrincipal)
            inflate(R.menu.novo_opcoes)
            show()
        }
    }

    override fun onMenuItemClick( item:MenuItem ):Boolean {
        return when (item.itemId) {
            R.id.acao_novo_item -> {
                inventarioViewModel.acao = InventarioViewModel.Acao.novoItem
                navegarParaEdicao()
                true
            }
            R.id.acao_novo_comodo -> {
                inventarioViewModel.acao = InventarioViewModel.Acao.novoComodo
                navegarParaEdicao()
                true
            }
            else -> false
        }
    }

    private fun navegarParaEdicao(){
        binding.fabNovo.hide()
        findNavController().navigate(R.id.action_telaPrincipal_to_telaEdicao)
    }

}
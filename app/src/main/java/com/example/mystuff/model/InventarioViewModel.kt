package com.example.mystuff.model

import androidx.lifecycle.ViewModel

class InventarioViewModel: ViewModel() {

    var usuarioAtual:Usuario? = null

    var itemSelecionado:Item? = null
    var comodoSelecionado:Comodo? = null

}
package com.example.mystuff

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.mystuff.databinding.ActivityMainBinding
import com.example.mystuff.model.InventarioViewModel
import com.example.mystuff.services.ComodoFSService
import com.example.mystuff.services.ItemFSService

class MainActivity : AppCompatActivity() {

    private val inventarioViewModel:InventarioViewModel by viewModels()
    private lateinit var _binding:ActivityMainBinding
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.appBar)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.acao_logout -> {
            inventarioViewModel.logout()
            binding.navegacaoPrincipal.findNavController().navigate(R.id.action_global_telaLogin)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu( menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

}
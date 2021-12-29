package com.clone.orgs.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone.orgs.dao.ProdutosDAO
import com.clone.orgs.databinding.ActivityMainBinding
import com.clone.orgs.helpers.ImageLoaderCoil
import com.clone.orgs.ui.recyclerview.adapters.ListaProdutosAdapter

class MainActivity : AppCompatActivity() {
    private val produtosDAO = ProdutosDAO()
    private val rAdapter = ListaProdutosAdapter(context = this, produtosDAO.buscaListaProdutos())
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ImageLoaderCoil().configuraImageLoaderCoil(this)
        configuraRecyclerView()
        configuraExtFab()
    }

    private fun configuraExtFab() {
        val fabAdd = binding.mainExtfabAddProduto
        val intent = Intent(this, FormularioActivity::class.java)

        fabAdd.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.mainRView
        recyclerView.adapter = rAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        rAdapter.atualizar(produtosDAO.buscaListaProdutos())
    }
}
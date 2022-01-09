package com.clone.orgs.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone.orgs.database.AppDatabase
import com.clone.orgs.database.dao.ProdutoDAO
import com.clone.orgs.databinding.ActivityMainBinding
import com.clone.orgs.helpers.AlertDialogDeletar
import com.clone.orgs.helpers.ImageLoaderCoil
import com.clone.orgs.modelos.Produto
import com.clone.orgs.ui.recyclerview.adapters.ListaProdutosAdapter

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var produtoDAO: ProdutoDAO
    private val rAdapter by lazy {
        ListaProdutosAdapter(
            context = this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ImageLoaderCoil().configuraImageLoaderCoil(this)

        val db = AppDatabase.getDb(this)

        produtoDAO = db.produtoDAO()

        configuraClickMenuPopUp()
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

    private fun configuraClickMenuPopUp(){
        rAdapter.menuListener = object : ListaProdutosAdapter.MenuClickListener {
            override fun clickEditar(produto: Produto) {
                Intent(this@MainActivity, FormularioActivity::class.java).apply {
                    putExtra("produto_edit", produto)
                    startActivity(this)
                }
            }

            override fun clickDeletar(produto: Produto) {
                AlertDialogDeletar.buildAndShow(this@MainActivity){
                    produtoDAO.deletarProduto(produto)
                    rAdapter.atualizar(produtoDAO.buscaListaProdutos())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        rAdapter.atualizar(produtoDAO.buscaListaProdutos())
    }
}
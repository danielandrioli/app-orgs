package com.clone.orgs.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.clone.orgs.R
import com.clone.orgs.database.AppDatabase
import com.clone.orgs.database.dao.ProdutoDAO
import com.clone.orgs.databinding.ActivityMainBinding
import com.clone.orgs.ui.dialog.AlertDialogDeletar
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

        ImageLoaderCoil.getAndSetImageLoaderCoil(this)

        val db = AppDatabase.getDb(applicationContext)

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

    private fun configuraClickMenuPopUp() {
        rAdapter.menuListener = object : ListaProdutosAdapter.MenuClickListener {
            override fun clickEditar(produto: Produto) {
                Intent(this@MainActivity, FormularioActivity::class.java).apply {
                    putExtra("id_produto_edit", produto.id)
                    startActivity(this)
                }
            }

            override fun clickDeletar(produto: Produto) {
                AlertDialogDeletar.buildAndShow(this@MainActivity) {
                    produtoDAO.deletarProduto(produto)
                    rAdapter.atualizar(produtoDAO.buscaListaProdutos())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordenacao, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.it_ordem_nome_asc -> rAdapter.atualizar(produtoDAO.buscaListaNomeAsc())
            R.id.it_ordem_nome_desc -> rAdapter.atualizar(produtoDAO.buscaListaNomeDesc())
            R.id.it_ordem_valor_asc -> rAdapter.atualizar(produtoDAO.buscaListaPrecoAsc())
            R.id.it_ordem_valor_desc -> rAdapter.atualizar(produtoDAO.buscaListaPrecoDesc())
            R.id.it_ordem_criacao -> rAdapter.atualizar(produtoDAO.buscaListaProdutos())
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        rAdapter.atualizar(produtoDAO.buscaListaProdutos())
    }
}
package com.clone.orgs.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import coil.load
import com.clone.orgs.R
import com.clone.orgs.database.AppDatabase
import com.clone.orgs.databinding.ActivityDetalhesProdutoBinding
import com.clone.orgs.helpers.AlertDialogDeletar
import com.clone.orgs.helpers.FormatadorMoeda
import com.clone.orgs.modelos.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetalhesProdutoBinding.inflate(layoutInflater) }
    private lateinit var produto: Produto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        produto = intent.extras?.get("produto") as Produto
        binding.apply {
            aDetalhesImg.load(produto.urlImagem)
            aDetalhesPreco.text = FormatadorMoeda().formataMoedaBr(produto.valor)
            aDetalhesTitulo.text = produto.nome
            aDetalhesTexto.text = produto.descricao
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_deletar -> {
                AlertDialogDeletar.buildAndShow(this) {
                    AppDatabase.getDb(this).produtoDAO().deletarProduto(produto)
                    finish()
                }
            }
            R.id.it_editar -> {
                val intent = Intent(this, FormularioActivity::class.java)
                intent.putExtra("produto_edit", produto)
                startActivity(intent)
                finish()
            }
        }
        return true
    }
}
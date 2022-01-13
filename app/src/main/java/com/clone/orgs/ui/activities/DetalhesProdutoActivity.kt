package com.clone.orgs.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.clone.orgs.R
import com.clone.orgs.database.AppDatabase
import com.clone.orgs.databinding.ActivityDetalhesProdutoBinding
import com.clone.orgs.ui.dialog.AlertDialogDeletar
import com.clone.orgs.helpers.FormatadorMoeda
import com.clone.orgs.modelos.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetalhesProdutoBinding.inflate(layoutInflater) }
    private var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        carregaProduto()
        super.onResume()
    }

    private fun carregaProduto() {
        val produtoId = intent.getLongExtra("produto_id", 0L)
//        val produtoId = intent.extras?.get("produto_id")?.let{ it as Long } //o que eu tava utilizando antes. O retorno era Any? e eu convertia para Long
//        val produtin = intent.getParcelableExtra<Produto>("produto") //esse era o código utilizado pelo professor para captar o Produto

        produto = AppDatabase.getDb(this).produtoDAO().buscaProduto(produtoId)
        produto?.let {
            binding.apply {
                if (it.urlImagem == null) aDetalhesImg.load(R.drawable.imgnotfound)
                else{
                    aDetalhesImg.load(it.urlImagem)
                }
                aDetalhesPreco.text = FormatadorMoeda().formataMoedaBr(it.valor)
                aDetalhesTitulo.text = it.nome
                aDetalhesTexto.text = it.descricao
            }
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
                    produto?.let {
                        AppDatabase.getDb(this).produtoDAO().deletarProduto(it)
                    }
                    finish()
                }
            }
            R.id.it_editar -> {
                val intent = Intent(this, FormularioActivity::class.java)
                produto?.let { intent.putExtra("id_produto_edit", it.id) }
                startActivity(intent)
            }
        }
        return true
    }
}
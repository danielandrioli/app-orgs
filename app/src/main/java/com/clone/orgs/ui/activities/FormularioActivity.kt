package com.clone.orgs.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.clone.orgs.database.AppDatabase
import com.clone.orgs.databinding.ActivityFormularioProdutosBinding
import com.clone.orgs.modelos.Produto
import com.clone.orgs.ui.dialog.FormularioImgDialog
import java.math.BigDecimal

private const val tagFormularioActivity = "LogFormularioActivity"

class FormularioActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFormularioProdutosBinding.inflate(layoutInflater) }
    private var urlImagem: String? = null
    private var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"

        val produtoEdit = intent.extras?.let {
            it.get("produto_edit") as Produto
        }
        if(produtoEdit != null){
            binding.apply {
                formularioEtDescProduto.editText?.setText(produtoEdit.descricao)
                formularioEtNomeProduto.editText?.setText(produtoEdit.nome)
                formularioEtPrecoProduto.editText?.setText(produtoEdit.valor.toString())
                urlImagem = produtoEdit.urlImagem
                formularioImgProduto.load(urlImagem)
                produto = produtoEdit
            }
            title = "Editar produto"
        }

        configurarBotaoSalvar()
        configuraImagem()
    }

    private fun configuraImagem() {
        val imagem = binding.formularioImgProduto

        imagem.setOnClickListener {
            FormularioImgDialog(this).mostrarDialog(urlImagem) { url ->
                urlImagem = url
                imagem.load(urlImagem)
            }
        }
    }

    private fun configurarBotaoSalvar() {
        val campoNomeProduto = binding.formularioEtNomeProduto
        val campoDescProduto = binding.formularioEtDescProduto
        val campoPrecoProduto = binding.formularioEtPrecoProduto

        val db = AppDatabase.getDb(this)

        val produtoDAO = db.produtoDAO()

        val botaoSalvar = binding.formularioBtnSalvar
        botaoSalvar.setOnClickListener {
            val nome: String = campoNomeProduto.editText?.text.toString()
            val descProduto: String = campoDescProduto.editText?.text.toString()
            val precoEmTexto: String = campoPrecoProduto.editText?.text.toString()

            val preco = if (precoEmTexto.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(precoEmTexto)
            }

            val id = produto?.id ?: 0L //id com valor 0 serve pro Room criar um novo produto.
            val produtoNovo = Produto(nome, descProduto, preco, urlImagem, id)

            if (produto == null){
                produtoDAO.inserirProduto(produtoNovo)
            }else{
                produtoDAO.editaProduto(produtoNovo)
            }
            Log.i(tagFormularioActivity, produtoNovo.toString())
            finish()
        }
    }
}
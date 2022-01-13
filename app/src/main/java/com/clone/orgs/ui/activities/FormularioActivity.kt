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
    private val produtoDAO by lazy { AppDatabase.getDb(this).produtoDAO() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"

        val idProduto = intent.extras?.get("id_produto_edit")?.let {
            it as Long //poderia utilizar o getLongExtra, mas deixei esse código para fins didáticos.
        }
        if (idProduto != null) {
            produto = produtoDAO.buscaProduto(idProduto)
            preencheCampos()
        }

        configurarBotaoSalvar()
        configuraImagem()
    }

    private fun preencheCampos() {
        binding.apply {
            produto?.let { produto ->
                formularioEtDescProduto.editText?.setText(produto.descricao)
                formularioEtNomeProduto.editText?.setText(produto.nome)
                formularioEtPrecoProduto.editText?.setText(produto.valor.toPlainString())
                urlImagem = produto.urlImagem
                formularioImgProduto.load(urlImagem)
            }
        }
        title = "Editar produto"
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
        binding.apply {
            formularioBtnSalvar.setOnClickListener {
                val nome: String = formularioEtNomeProduto.editText?.text.toString()
                val descProduto: String = formularioEtDescProduto.editText?.text.toString()
                val precoEmTexto: String = formularioEtPrecoProduto.editText?.text.toString()

                val preco = if (precoEmTexto.isBlank()) {
                    BigDecimal.ZERO
                } else {
                    BigDecimal(precoEmTexto)
                }

                val id = produto?.id ?: 0L //id com valor 0 serve pro Room criar um novo produto.
                val produtoNovo = Produto(nome, descProduto, preco, urlImagem, id)

                if (produto == null) {
                    produtoDAO.inserirProduto(produtoNovo)
                } else {
                    produtoDAO.editaProduto(produtoNovo)
                }
                Log.i(tagFormularioActivity, produtoNovo.toString())
                finish()
            }
        }
    }
}
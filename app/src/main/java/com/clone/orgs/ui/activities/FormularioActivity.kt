package com.clone.orgs.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.clone.orgs.dao.ProdutosDAO
import com.clone.orgs.databinding.ActivityFormularioProdutosBinding
import com.clone.orgs.modelos.Produto
import com.clone.orgs.ui.dialog.FormularioImgDialog
import java.math.BigDecimal

private const val tagFormularioActivity = "LogFormularioActivity"

class FormularioActivity : AppCompatActivity() {
    private val binding by lazy { ActivityFormularioProdutosBinding.inflate(layoutInflater) }
    private var urlImagem: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        title = "Cadastrar produto"
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
        val produtosDAO = ProdutosDAO()

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

            val produtoNovo = Produto(nome, descProduto, preco, urlImagem)
            Log.i(tagFormularioActivity, produtoNovo.toString())
            produtosDAO.adicionar(produtoNovo)
            finish()
        }
    }
}
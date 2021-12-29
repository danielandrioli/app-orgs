package com.clone.orgs.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.clone.orgs.R
import com.clone.orgs.databinding.ActivityDetalhesProdutoBinding
import com.clone.orgs.helpers.FormatadorMoeda
import com.clone.orgs.modelos.Produto

class DetalhesProdutoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetalhesProdutoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val produto: Produto = intent.extras?.get("produto") as Produto
        binding.apply {
            aDetalhesImg.load(produto.urlImagem)
            aDetalhesPreco.text = FormatadorMoeda().formataMoedaBr(produto.valor)
            aDetalhesTitulo.text = produto.nome
            aDetalhesTexto.text = produto.descricao
        }
    }
}
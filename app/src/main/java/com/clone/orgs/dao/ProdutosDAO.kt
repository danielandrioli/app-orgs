package com.clone.orgs.dao

import android.util.Log
import com.clone.orgs.modelos.Produto
//DAO = Data Access Object

private const val tagProdutosDAO = "LogProdutosDAO"

class ProdutosDAO {

    fun adicionar(produto: Produto){
        listaProdutos.add(produto)
        Log.i(tagProdutosDAO, "Produto '${produto.nome}' adicionado com sucesso!")
    }

    fun buscaListaProdutos(): List<Produto>{
        return listaProdutos.toList() //Essa é uma cópia, e não a referência de listaProdutos. Dessa
                                    //forma, quem acessar a lista não poderá manipular a original
    }

    companion object {
        private val listaProdutos = mutableListOf<Produto>()
    }
}
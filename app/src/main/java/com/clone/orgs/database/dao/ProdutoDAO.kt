package com.clone.orgs.database.dao

import androidx.room.*
import com.clone.orgs.modelos.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    fun buscaListaProdutos(): List<Produto>

    @Insert
    fun inserirProduto(produto: Produto)

    @Delete
    fun deletarProduto(produto: Produto)

    @Insert
    fun insereTodos(vararg produto: Produto)

    @Update
    fun editaProduto(produto: Produto)
}
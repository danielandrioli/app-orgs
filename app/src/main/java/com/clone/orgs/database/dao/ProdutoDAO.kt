package com.clone.orgs.database.dao

import androidx.room.*
import com.clone.orgs.modelos.Produto

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Produto")
    fun buscaListaProdutos(): List<Produto>

    @Query("SELECT * FROM Produto WHERE id = :idProduto")
    fun buscaProduto(idProduto: Long): Produto?
    //Se o id não existir na tabela, um valor nulo será enviado.

    //Com esse argumento, caso o id já exista então haverá edição. Assim, não há necessidade de utilizar um método com @Update.
    @Insert(onConflict = OnConflictStrategy.REPLACE) // De qualquer forma, vou utiliza-lo para fins didáticos
    fun inserirProduto(produto: Produto)

    @Delete
    fun deletarProduto(produto: Produto)

    @Insert
    fun insereTodos(vararg produto: Produto)

    @Update
    fun editaProduto(produto: Produto)

    @Query("SELECT * FROM Produto ORDER BY nome ASC")
    fun buscaListaNomeAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome DESC")
    fun buscaListaNomeDesc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor ASC")
    fun buscaListaPrecoAsc(): List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor DESC")
    fun buscaListaPrecoDesc(): List<Produto>
}
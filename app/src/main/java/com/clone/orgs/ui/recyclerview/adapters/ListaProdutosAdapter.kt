package com.clone.orgs.ui.recyclerview.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.clone.orgs.R
import com.clone.orgs.databinding.ProdutoItemBinding
import com.clone.orgs.helpers.FormatadorMoeda
import com.clone.orgs.modelos.Produto
import com.clone.orgs.ui.activities.DetalhesProdutoActivity
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListaProdutosAdapter(
    private val context: Context,
    private var listaProdutos: List<Produto> = emptyList()
) : RecyclerView.Adapter<ListaProdutosAdapter.MeuViewHolder>() {
    lateinit var menuListener: MenuClickListener

    /*O professor Alex Felipe deixou a listaProdutos apenas como um parâmetro normal, sem ser property.
    * Então ele criou um property no escopo da classe que é um mutableList, deixou private e val, e atribuiu a lista
    * recebida no parâmetro listaProdutos a essa nova lista, utilizando o método da list toMutableList.
    * Essa lista, que é uma property, é a que é utilizada dentro desse adapter. No método 'atualiza', ela sofre um clear
    * e addAll da lista recebida como parâmetro nesse método.
    * val private listaProdutos = listaProdutos.toMutableList()
    * Segundo ele, é comum chamar essa lista utilizada internamente no adapter como 'dataset'*/

    interface MenuClickListener{
        fun clickEditar(produto: Produto)
        fun clickDeletar(produto: Produto)
    }

    inner class MeuViewHolder(binding: ProdutoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nome = binding.produtoTxtNome
        val descricao = binding.produtoTxtDesc
        val valor = binding.produtoTxtValor
        val imagem = binding.produtoImage

        fun vincular(produto: Produto) {
            nome.text = produto.nome
            descricao.text = produto.descricao
            valor.text = FormatadorMoeda().formataMoedaBr(produto.valor)
            if(produto.urlImagem != null){
                imagem.load(produto.urlImagem)
            } else{
                imagem.visibility = View.GONE
            }

            itemView.setOnClickListener {
                val intent = Intent(context, DetalhesProdutoActivity::class.java)
                intent.putExtra("produto", produto)
                context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                val popUpMenu = PopupMenu(context, itemView)
                MenuInflater(context).inflate(R.menu.menu_detalhes_produto, popUpMenu.menu)
                popUpMenu.show()

                popUpMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.it_editar -> menuListener.clickEditar(produto)
                        R.id.it_deletar -> menuListener.clickDeletar(produto)
                    }
                    true
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeuViewHolder {
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MeuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeuViewHolder, position: Int) {
        val produto = listaProdutos[position]
        holder.vincular(produto)
    }

    override fun getItemCount(): Int = listaProdutos.size

    fun atualizar(listaAtualizada: List<Produto>) {
        listaProdutos = listaAtualizada
        notifyDataSetChanged()
    }
}

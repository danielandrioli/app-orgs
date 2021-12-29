package com.clone.orgs.ui.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import coil.load
import com.clone.orgs.databinding.FormularioImagemBinding

class FormularioImgDialog(private val context: Context) {

    fun mostrarDialog(urlPadrao: String? = null, whenLoaded: (url: String) -> Unit){
        //quem chamar o mostrarDialog vai ter que enviar uma função como parâmetro.
        //no setPositiveButton, essa função é chamada. O que essa função faz? Isso quem decide é
        //quem chama o mostrarDialog e envia a função como parâmetro.

        //O apply faz com que o código dentro das chaves esteja no escopo do objeto que aplica o apply.
        //Com isso, não foi necessário colocar esse objeto numa variável e utiliza-la cada vez que eu fosse
        //chamar um recurso dela (método ou atributo), como foi o caso de formularioimgEditText, formularioimgImagem...
        FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {
            val btnCarregar = formularioimgBtnCarregar

            if (urlPadrao != null){
                formularioimgEditText.setText(urlPadrao)
                formularioimgImagem.load(urlPadrao)
            }

            val imgDialog = formularioimgImagem
            btnCarregar.setOnClickListener {
                val url = formularioimgEditText.text.toString()
                imgDialog.load(url)
                Log.i("url formulario", "url: $url")
            }

            AlertDialog.Builder(context)
                .setPositiveButton("Confirmar") { _, _ ->
                    val urlImagem = formularioimgEditText.text.toString()
                    whenLoaded(urlImagem)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                }
                .setView(root)
                .show()
        }
    }
}
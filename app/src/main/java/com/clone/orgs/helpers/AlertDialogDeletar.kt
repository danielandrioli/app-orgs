package com.clone.orgs.helpers

import android.content.Context
import androidx.appcompat.app.AlertDialog

class AlertDialogDeletar {

    companion object {
        fun buildAndShow(context: Context, executaDelecao: () -> Unit) {
            AlertDialog.Builder(context)
                .setNegativeButton("Cancelar") { _, _ ->
                }
                .setPositiveButton("Deletar") { _, _ ->
                    executaDelecao()
                }
                .setMessage("Tem certeza que deseja deletar o produto?")
                .setTitle("Atenção!")
                .show()
        }
    }
}
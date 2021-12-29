package com.clone.orgs.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
class Produto(
        val nome: String,
        val descricao: String,
        val valor: BigDecimal,
        val urlImagem: String?
): Parcelable{
        override fun toString(): String {
                return "$nome - $descricao - $valor"
        }
}

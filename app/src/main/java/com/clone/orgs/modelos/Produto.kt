package com.clone.orgs.modelos

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
class Produto(
        val nome: String,
        @ColumnInfo(name = "descricao_produto") val descricao: String,
        val valor: BigDecimal,
        val urlImagem: String?,
        @PrimaryKey(autoGenerate = true) val id: Long = 0L
): Parcelable{
        override fun toString(): String {
                return "$nome - $descricao - $valor"
        }
}

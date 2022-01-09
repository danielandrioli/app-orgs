package com.clone.orgs.database.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter //com essa fun abaixo, pretendo resgatar dado double para o objeto q contém bigdecimal
    fun doubleToBigDecimal(dodo: Double?): BigDecimal{//a property do objeto não aceita null
        return dodo?.let { BigDecimal(it.toString()) } ?: BigDecimal.ZERO
    }//Ao criar BigDecimal assim, é melhor cria-lo a partir de String.

    @TypeConverter //com essa fun abaixo pretendo salvar o bigdecimal no db.
    fun bigDecimalToDouble(bigD: BigDecimal?): Double?{//é possível mandar um null para o DB
        return bigD?.toDouble()         //a não ser q vc configure NOT NULL, claro.
    }
}
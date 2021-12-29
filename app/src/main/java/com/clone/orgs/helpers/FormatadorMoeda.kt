package com.clone.orgs.helpers

import java.text.NumberFormat
import java.util.*

class FormatadorMoeda {

    fun formataMoedaBr(valor: Number): String{
        return NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(valor)
    }
}
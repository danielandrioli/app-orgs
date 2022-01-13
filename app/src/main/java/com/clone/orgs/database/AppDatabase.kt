package com.clone.orgs.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.clone.orgs.database.converters.Converters
import com.clone.orgs.database.dao.ProdutoDAO
import com.clone.orgs.modelos.Produto

@TypeConverters(Converters::class)
@Database(entities = [Produto::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun produtoDAO(): ProdutoDAO

    companion object {
        @Volatile
        private lateinit var db: AppDatabase

        fun getDb(context: Context): AppDatabase {
            if (::db.isInitialized) return db
            return Room.databaseBuilder(context, AppDatabase::class.java, "db_orgs")
                .allowMainThreadQueries()
                .build().also {
                    db = it
                    Log.i("Db", "Database instanciado.")
                }
        }
    }
}
//Como a criação de um database é custosa, o ideal é trabalhar com o padrão de projeto Singleton.
/*
* @Volatile é uma anotação para indicar para todas as threads quando um valor é inserido na property.
* Isso é interessante para que outras threads não atribuam um novo valor à property.
* Dessa forma, garantimos a estabilidade, integridade e unicidade do singleton.
* Lembre-se de que acessar o Room de forma assíncrona/paralela é a ação esperada.
* */
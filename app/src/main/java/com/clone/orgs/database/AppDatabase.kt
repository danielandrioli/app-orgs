package com.clone.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.clone.orgs.database.converters.Converters
import com.clone.orgs.database.dao.ProdutoDAO
import com.clone.orgs.modelos.Produto

@TypeConverters(Converters::class)
@Database(entities = [Produto::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun produtoDAO(): ProdutoDAO

    companion object{
        fun getDb(context: Context): AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, "db_orgs")
                .allowMainThreadQueries()
                .build()
        }
    }
}
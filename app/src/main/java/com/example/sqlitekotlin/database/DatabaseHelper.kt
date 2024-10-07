package com.example.sqlitekotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    //1)Contexto 2)Nome do banco de dados 3)CursorFactory 4)Versão do banco de dados
    context, "loja.db", null,1  //1produtos  2produtos e vendas

) {

    companion object{
        const val TABELA_PRODUTOS = "produtos"
        const val ID_PRODUTO = "id_produtos"
        const val TABELA_TITULO = "titulo"
        const val DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $TABELA_PRODUTOS (" +
                "$ID_PRODUTO integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$TABELA_TITULO VARCHAR(24)," +
                "$DESCRICAO text" +
                ");"

        try {
            db?.execSQL(sql)
            Log.i("info_db","Sucesso ao criar a tabela")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db","Erro ao criar a tabela")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Criar tabela de vendas

    }
}
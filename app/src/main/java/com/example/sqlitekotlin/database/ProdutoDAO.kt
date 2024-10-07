package com.example.sqlitekotlin.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.sqlitekotlin.model.Produto

class ProdutoDAO(context: Context): IProdutoDAO{

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase


    override fun salvar(produto: Produto): Boolean {
//        val titulo = produto.titulo
//        val sql = "INSERT INTO produtos VALUES (null,'$titulo','Descricao...');"

        val valores = ContentValues()
        valores.put("${DatabaseHelper.TABELA_TITULO}", produto.titulo)
        valores.put("${DatabaseHelper.DESCRICAO}", produto.descricao)

        try {
//            escrita.execSQL(sql)
            escrita.insert(
                DatabaseHelper.TABELA_PRODUTOS,
                null,
                valores
            )
        }catch (e: Exception){
            return false
        }
        return true
    }

    override fun atualizar(produto: Produto): Boolean {
        /*
        val titulo = produto.titulo
        val idProduto = produto.idProduto
        val sql = "UPDATE ${DatabaseHelper.TABELA_PRODUTOS}" +
                " SET ${DatabaseHelper.TABELA_TITULO} ='$titulo' WHERE ${DatabaseHelper.ID_PRODUTO} = $idProduto;"
                */
        val valores = ContentValues()
        valores.put("${DatabaseHelper.TABELA_TITULO}", produto.titulo)
        valores.put("${DatabaseHelper.DESCRICAO}", produto.descricao)
        val args = arrayOf(produto.idProduto.toString())
        try {
            escrita.update(
                DatabaseHelper.TABELA_PRODUTOS,
                valores,
                "id_produto = ?",
                args
            )
        }catch (e: Exception){
            return false
        }
        return true
    }

    override fun remover(idProduto: Int): Boolean {
        /*val sql = "DELETE FROM ${DatabaseHelper.TABELA_PRODUTOS} WHERE ${DatabaseHelper.ID_PRODUTO} = $idProduto;"*/
        val args = arrayOf(idProduto.toString())
        try {
            escrita.delete(
                DatabaseHelper.TABELA_PRODUTOS,
                "${DatabaseHelper.ID_PRODUTO} = ?",
                args
            )
        }catch (e: Exception){
            return false
        }
        return true
    }

    override fun listar(): List<Produto> {
        val listaProduto = mutableListOf<Produto>()


        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_PRODUTOS};"
        val cursor = leitura.rawQuery(sql, null)
        while (cursor.moveToNext()){

            val indiceId = cursor.getColumnIndex("${DatabaseHelper.ID_PRODUTO}")
            val indiceTitulo = cursor.getColumnIndex("${DatabaseHelper.TABELA_TITULO}")
            val indiceDescricao = cursor.getColumnIndex("${DatabaseHelper.DESCRICAO}")

            val idProduto = cursor.getInt(indiceId)
            val titulo = cursor.getString(indiceTitulo)
            val descricao = cursor.getString(indiceDescricao)

            Log.i("info.db","id, ${idProduto}, ${titulo}, $descricao")

            listaProduto.add(
                Produto(idProduto, titulo, descricao )
            )
        }
        return listaProduto
    }


}
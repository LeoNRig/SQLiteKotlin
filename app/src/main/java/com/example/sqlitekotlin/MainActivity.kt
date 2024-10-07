package com.example.sqlitekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sqlitekotlin.database.DatabaseHelper
import com.example.sqlitekotlin.database.ProdutoDAO
import com.example.sqlitekotlin.databinding.ActivityMainBinding
import com.example.sqlitekotlin.model.Produto

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bancoDados by lazy{
        DatabaseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            btnSalvar.setOnClickListener{
                salvar()
            }
            btnLista.setOnClickListener {
                listar()
            }
            btnAtualizar.setOnClickListener{
                atualizar()
            }
            btnRemove.setOnClickListener {
                remover()
            }
        }

    }

    private fun remover() {

        val produtoDAO = ProdutoDAO(this)
        produtoDAO.remover(3)

    }

    private fun atualizar() {
        val titulo = binding.editTextText.text.toString()
        val produtoDAO = ProdutoDAO(this)
        val produto = Produto(
            -1, titulo,"descricao..."
        )
        produtoDAO.atualizar(produto)

    }

    private fun listar() {
        val produtoDAO = ProdutoDAO(this)
        val listaProduto = produtoDAO.listar()
        var texto = ""

        if(listaProduto.isNotEmpty()){
            listaProduto.forEach {produto ->
                texto += "${produto.idProduto} - ${produto.titulo} \n"
                Log.i("info_db","${produto.idProduto} - ${produto.titulo}")
            }
            binding.textResultado.text = texto
        }else{
            binding.textResultado.text = "Nenhum produto cadastrado"
        }
    }

    private fun salvar(){

        val titulo = binding.editTextText.text.toString()
        val produtoDAO = ProdutoDAO(this)
        val produto = Produto(
            -1, titulo,"descricao..."
        )
        if(produtoDAO.salvar(produto)){
            Toast.makeText(this, "Sucesso ao Cadastrar produto", Toast.LENGTH_SHORT).show()
            binding.editTextText.setText("")
        }else{
            Toast.makeText(this, "Falha ao Cadastrar produto", Toast.LENGTH_SHORT).show()
        }

    }
}
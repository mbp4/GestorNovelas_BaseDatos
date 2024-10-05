package com.example.gestornovelas_basedatos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class NuevaNovelaActivity: ComponentActivity() {
    private lateinit var btnGuardarNovela: Button
    private lateinit var btnCancelar: Button
    private lateinit var editTitulo: EditText
    private lateinit var editAutor: EditText
    private lateinit var editAño: EditText
    private lateinit var editSinopsis: EditText
    private var listadoNovelas: MutableList <Novela> = NovelasRepository.novelas
    //creamos todas las variables necesarias para hacer la activity funcional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_novela) //asignamos el correspodinete layput

        editTitulo = findViewById(R.id.editTitulo)
        editAutor = findViewById(R.id.editAutor)
        editAño = findViewById(R.id.editAño)
        editSinopsis = findViewById(R.id.editSinopsis)
        //asignamos a cada una de las variables anteriores las definidias en el layout asigando

        btnGuardarNovela = findViewById(R.id.btnGuardarNovela)
        btnCancelar = findViewById(R.id.btnCancelar)
        //hacemos lo mismo con los botones

        btnGuardarNovela.setOnClickListener {
            guardarNovela()
            finish()
            //boton encargado de guardar una novela nueva
        }

        btnCancelar.setOnClickListener {
            finish()
            //vuelve a la actividad inicial
        }

    }

    fun guardarNovela(){

        val titulo = editTitulo.text.toString()
        val autor = editAutor.text.toString()
        val año = editAño.text.toString().toInt()
        val sinopsis = editSinopsis.text.toString()
        val nuevaNovela = Novela(titulo, autor, año, sinopsis)
        //creamos una nueva novela con sus correspondientes atributos

        listadoNovelas.add(nuevaNovela)
        //añadimos la novela al listado

        val resultIntent = Intent()
        setResult(RESULT_OK, resultIntent)
        //por ultimo mandamos la señal de que se ha guardado de manera correcta para poder a añadirla al listado y que este se actualice
    }
}
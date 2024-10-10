package com.example.gestornovelas_basedatos

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class VerNovelaActivity: ComponentActivity() {
    private lateinit var txt1: TextView
    private lateinit var txt2: TextView
    private lateinit var txt3: TextView
    private lateinit var txt4: TextView
    private lateinit var txt5: TextView
    private lateinit var btnVolver: Button
    //creamos las variables necesarias para poder hacer la activity funcional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver) //asociamos un layout
        txt1 = findViewById(R.id.txt1)
        val titulo = intent.getStringExtra("Titulo")
        txt1.text = titulo

        txt2 = findViewById(R.id.txt2)
        val autor = intent.getStringExtra("Autor")
        txt2.text = autor

        txt3 = findViewById(R.id.txt3)
        val sinopsis = intent.getStringExtra("Sinopsis")
        txt3.text = sinopsis

        txt4 = findViewById(R.id.txt4)
        val año = intent.getIntExtra("Año", 0).toString()
        txt4.text = año

        txt5 = findViewById(R.id.txt5)
        val fav = intent.getBooleanExtra("Favorita", false)

        if (fav) {
            txt5.text = "Sí"
        } else {
            txt5.text = "No"
        }

        //le damos a cada uno de los atributos necesarios su valor correspondiente para que se muestre por pantalla

        btnVolver = findViewById(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }
        //creamos el boton que nos llevara de vuelta a la aplicación inicial

    }

}
package com.example.gestornovelas_basedatos

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class AcercaDeActivity: ComponentActivity(){
    private lateinit var btnAceptar: Button //creamos un boton que asociaremos al boton de la vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de) //asociamos el correspondiente layout

        btnAceptar = findViewById(R.id.aceptar)

        btnAceptar.setOnClickListener {
            finish()
            //cuando se pulse el boton termianmos la aplicacion y volvemos a la actividad inicial
        }
    }
}
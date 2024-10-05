package com.example.gestornovelas_basedatos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestornovelas_basedatos.ui.theme.GestorNovelas_BaseDatosTheme

class MainActivity : ComponentActivity() {
    private lateinit var btnAlta: Button
    private lateinit var btnAcercaDe: Button
    private lateinit var recyclerNovelas: RecyclerView
    private lateinit var novelasAdapter: NovelasAdapter
    private var listadoNovelas: MutableList <Novela> = NovelasRepository.novelas
    //declaramos todas las variables necesarias para hacer la aplicación funcional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //se establece la vista de la actividad

        btnAlta = findViewById(R.id.btnAlta)
        btnAcercaDe = findViewById(R.id.btnAcercaDe)
        //asociamos a los botones el identificador del boton del layout

        btnAlta.setOnClickListener {
            val intent = Intent(this, NuevaNovelaActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ALTA_NOVELA)
        } //iniciamos la actividad y para que la lista se reinicie al añadir o no un nuevo elemento

        btnAcercaDe.setOnClickListener {
            val intent = Intent(this, AcercaDeActivity::class.java)
            startActivity(intent)
        }
        //iniciamos la nueva actividad acerca de

        novelasAdapter = NovelasAdapter(listadoNovelas){
                novela -> val intent = Intent(this, VerNovelaActivity::class.java)
            intent.putExtra("Titulo", novela.titulo)
            intent.putExtra("Autor", novela.autor)
            intent.putExtra("Año", novela.año)
            intent.putExtra("Sinopsis", novela.sinopsis)
            startActivity(intent)
        }
        //iniciamos el adaptador del recyclerview con una lista de novelas pasando la informacion de la novela que se quiere visualizar

        recyclerNovelas = findViewById(R.id.recyclerNovelas) //iniciamos el recycler
        recyclerNovelas.adapter = novelasAdapter //asociamos el adaptar
        recyclerNovelas.layoutManager = LinearLayoutManager(this) //y asociamos el linear layput

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ALTA_NOVELA && resultCode == RESULT_OK) {
            novelasAdapter.notifyDataSetChanged()
        }
        //con esta funcion conseguimos que en el caso de que se haya creado una nueva novela y el resultado haya sido exitoso
    }

    companion object {
        private const val REQUEST_CODE_ALTA_NOVELA = 1
    }
    //creamos una constante que identifica la peticion de alta de una novela

}

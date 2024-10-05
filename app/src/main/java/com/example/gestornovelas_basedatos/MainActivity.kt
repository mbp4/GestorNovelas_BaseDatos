package com.example.gestornovelas_basedatos

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
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
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    private lateinit var btnAlta: Button
    private lateinit var btnAcercaDe: Button
    private lateinit var recyclerNovelas: RecyclerView
    private lateinit var novelasAdapter: NovelasAdapter
    private var listadoNovelasF: MutableList <Novela> = mutableListOf()
    private val db: FirebaseFirestore = Firebase.firestore

    companion object{
        const val ACCION_VER = 1
        const val ACCION_BORRAR = 2
    }
    //declaramos todas las variables necesarias para hacer la aplicación funcional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //se establece la vista de la actividad

        btnAlta = findViewById(R.id.btnAlta)
        btnAcercaDe = findViewById(R.id.btnAcercaDe)
        recyclerNovelas = findViewById(R.id.recyclerNovelas)

        //asociamos a los botones el identificador del boton del layout

        btnAlta.setOnClickListener {
            val intent = Intent(this, NuevaNovelaActivity::class.java)
            startActivity(intent)
        } //iniciamos la actividad y para que la lista se reinicie al añadir o no un nuevo elemento

        btnAcercaDe.setOnClickListener {
            val intent = Intent(this, AcercaDeActivity::class.java)
            startActivity(intent)
        }

        mostrarNovelas()
    }

    override fun onResume() {
        super.onResume()
        mostrarNovelas()
    }

    private fun mostrarNovelas() {
        db.collection("novelas")
            .get()
            .addOnSuccessListener { documentos ->
                listadoNovelasF.clear()
                for (documento in documentos) {
                    val novela = documento.toObject(Novela::class.java)
                    listadoNovelasF.add(novela)
                }
                prepararRecyclerView()
            }
            .addOnFailureListener({ exception ->
                Toast.makeText(this, "Error al obtener las novelas", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Error al obtener las novelas de la base de datos", exception)
            })
    }

    private fun prepararRecyclerView(){
        recyclerNovelas.layoutManager = LinearLayoutManager(this)
        novelasAdapter = NovelasAdapter(listadoNovelasF){ novela, accion ->
            if (accion == ACCION_VER) {
                verNovela(novela)
            }
            else if (accion == ACCION_BORRAR){
                borrarNovela(novela)
            }

        }
        recyclerNovelas.adapter = novelasAdapter
        novelasAdapter.notifyDataSetChanged()
    }

    private fun verNovela(novela: Novela){
        val intent = Intent(this, VerNovelaActivity::class.java)
        intent.putExtra("Titulo", novela.titulo)
        intent.putExtra("Autor", novela.autor)
        intent.putExtra("Año", novela.año)
        intent.putExtra("Sinopsis", novela.sinopsis)
        startActivity(intent)
    }

    private fun borrarNovela(novela: Novela){
        db.collection("novelas")
            .whereEqualTo("titulo", novela.titulo)
            .get()
            .addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    documento.reference.delete()
                }
                mostrarNovelas()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al borrar la novela", Toast.LENGTH_SHORT).show()
            }
    }

}


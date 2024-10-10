package com.example.gestornovelas_basedatos

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    private lateinit var btnAlta: Button
    private lateinit var btnAcercaDe: Button
    private lateinit var recyclerNovelas: RecyclerView
    private lateinit var novelasAdapter: NovelasAdapter
    private var listadoNovelasF: MutableList<Novela> = mutableListOf()
    private val db: FirebaseFirestore = Firebase.firestore
    //creamos todas las variables necesarias para hacer la activity funcional

    companion object {
        const val ACCION_VER = 1
        const val ACCION_BORRAR = 2
        const val ACCION_FAV = 3
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
        } //boton que nos lleva a la actividad de alta de novelas

        btnAcercaDe.setOnClickListener {
            val intent = Intent(this, AcercaDeActivity::class.java)
            startActivity(intent)
            //boton que nos lleva a la actividad de acerca de
        }

        mostrarNovelas()

    }

    override fun onResume() {
        super.onResume()
        mostrarNovelas()
    }
    //creamos una función que haga que la lista se actualice al volver a la actividad

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
            }) //mandamos un error a la logcat y al usuario en el caso de que no se pueda obtener la lista de novelas de la base de datos
        //creamos un metodo que hace que muestre la lista de novelas de la base de datos y las añada a una lista de novelas para uqe se muestren en la principal
    }

    private fun prepararRecyclerView() {
        recyclerNovelas.layoutManager = LinearLayoutManager(this)
        //configuramos el recycler para que sea una lista vertical
        novelasAdapter = NovelasAdapter(listadoNovelasF) { novela, accion ->
            if (accion == ACCION_VER) {
                verNovela(novela)
            } else if (accion == ACCION_BORRAR) {
                borrarNovela(novela)
            } else if (accion == ACCION_FAV) {
                añadirFavorita(novela)
            }
            //hacemos que el metodo identifique si el usuario quiere borrar o ver la novela y se ejecuta la accion elegida

        }
        recyclerNovelas.adapter = novelasAdapter //asignamos el recycler a la vista
        novelasAdapter.notifyDataSetChanged() //notificamos al adaptador que los datos han cambiado
    }

    private fun verNovela(novela: Novela) {
        val intent = Intent(this, VerNovelaActivity::class.java)
        intent.putExtra("Titulo", novela.titulo)
        intent.putExtra("Autor", novela.autor)
        intent.putExtra("Año", novela.año)
        intent.putExtra("Sinopsis", novela.sinopsis)
        startActivity(intent)
        //mostramos todos los datos de la novela que el usuario ha elegido en una nueva pantalla
    }

    private fun borrarNovela(novela: Novela) {
        db.collection("novelas")
            .whereEqualTo("titulo", novela.titulo)
            .get()
            .addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    documento.reference.delete()
                }
                mostrarNovelas()
                Toast.makeText(this, "Novela eliminada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al borrar la novela", Toast.LENGTH_SHORT).show()
            }
    }
    //con este metodo buscamos la novela que el usuario ha elegido y la borramos de la base de datos dandole a la vez un mensaje para que sepa que se ha eliminado correctamente

    private fun añadirFavorita(novela: Novela) {
        db.collection("novelas")
            .whereEqualTo("titulo", novela.titulo)
            .get()
            .addOnSuccessListener { documentos ->
                for (documento in documentos) {
                    documento.reference.update("fav", true)
                }
                mostrarNovelas()
                Toast.makeText(this, "Novela añadida a favoritos", Toast.LENGTH_SHORT).show()
            }
    }
}



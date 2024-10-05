package com.example.gestornovelas_basedatos

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NovelasAdapter (private var novelas: MutableList<Novela>,
                      private val onNovelasClick: (Novela, Int) -> Unit): RecyclerView.Adapter<NovelasAdapter.NovelasViewHolder>(){

    class NovelasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textTituloNovel: TextView = itemView.findViewById(R.id.textTituloNovel)
        val textAutorNovel: TextView = itemView.findViewById(R.id.textAutorNovel)
        val btnVer: Button = itemView.findViewById(R.id.btnVer)
        val btnBorrar: Button = itemView.findViewById(R.id.btnBorrar)
        //creamos las correspodientes variables para que el activity sea funcional
    }

    //la clase adapter obliga a crear unos metodos por defecto

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NovelasViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_novela, parent, false)
        return NovelasViewHolder(view)
    }
    //este primer metodo crea una nueva vista cuando sea necesario, por lo que se crea una vista para cada una de las novelas de la lista y devuelve la vista

    override fun onBindViewHolder(holder: NovelasViewHolder, position: Int) {
        val currentNovela = novelas[position]
        holder.textTituloNovel.text = currentNovela.titulo
        holder.textAutorNovel.text = currentNovela.autor
        //al inicio solo queremos que se muestre la lista y de cada novela solo se mostrara el titulo y el autor de la misma
        holder.btnVer.setOnClickListener {
            onNovelasClick(currentNovela, MainActivity.ACCION_VER)
        }
        //para ver la informacion completa se pulsa el boton y se mostrará

        holder.btnBorrar.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Borrar novela")
                .setMessage("¿Estás seguro de que quieres borrar esta novela? ${currentNovela.titulo}")
                .setPositiveButton("Sí") { _, _ ->
                    // El usuario ha confirmado la eliminación
                    onNovelasClick(currentNovela, MainActivity.ACCION_BORRAR)
                }
                .setNegativeButton("No", null)
                .show()
        }
        //en el caso de querer borrar la novela se hara uso de este boton

    }

    override fun getItemCount(): Int {
        return novelas.size
        //devuelve el numero de items de la lista
    }
}


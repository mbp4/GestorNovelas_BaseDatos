package com.example.gestornovelas_basedatos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NovelasAdapter (private var novelas: MutableList<Novela>,
                      private val onNovelasClick: (Novela) -> Unit): RecyclerView.Adapter<NovelasAdapter.NovelasViewHolder>(){

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
        val novela = novelas[position]
        holder.textTituloNovel.text = novela.titulo
        holder.textAutorNovel.text = novela.autor
        //al inicio solo queremos que se muestre la lista y de cada novela solo se mostrara el titulo y el autor de la misma
        holder.btnVer.setOnClickListener {
            onNovelasClick(novela)
        }
        //para ver la informacion completa se pulsa el boton y se mostrará

        holder.btnBorrar.setOnClickListener {
            novelas.removeAt(position)
            notifyDataSetChanged()
        }
        //en el caso de querer borrar la novela se hara uso de este boton

    }

    override fun getItemCount(): Int {
        return novelas.size
        //devuelve el numero de items de la lista
    }
}


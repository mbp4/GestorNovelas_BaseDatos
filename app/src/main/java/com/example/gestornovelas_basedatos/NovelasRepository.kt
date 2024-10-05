package com.example.gestornovelas_basedatos

class NovelasRepository {
    companion object {
        val novelas = mutableListOf<Novela>().apply {
            add(Novela("La llamada de Cthulhu", "H. P. Lovecraft", 1928, "Este relato narra la investigación de un profesor que descubre pruebas sobre la existencia de una antigua y terrible entidad cósmica llamada Cthulhu, un dios primigenio que yace dormido en las profundidades del océano. A través de diversas fuentes y testimonios, se revela el impacto de este ser en los sueños y la locura de los humanos, sugiriendo que su despertar podría significar el fin de la humanidad."))
            add(Novela("Cien años de Soledad", "Gabriel García Márquez", 1967, "Narra la historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo. La novela explora temas como el realismo mágico, el destino y el olvido, a través de una narrativa que mezcla lo fantástico con lo cotidiano."))
            add(Novela("1984", "George Orwell", 1949, "Ambientada en una distopía totalitaria donde el gobierno, encabezado por el Gran Hermano, controla todos los aspectos de la vida. El protagonista, Winston Smith, lucha contra la opresión y busca la verdad en una sociedad manipulada por la propaganda y la vigilancia constante."))
            add(Novela("Orgullo y Prejucicio", "Jane Austen", 1813, " Esta obra retrata la vida y las relaciones en la Inglaterra rural del siglo XIX, centrándose en Elizabeth Bennet y su familia. La novela explora temas de clase, matrimonio y malentendidos, especialmente en la relación entre Elizabeth y el enigmático señor Darcy."))
            add(Novela("Crimen y castigo", "Fiódor Dostoyevski", 1866, "La novela sigue a Raskólnikov, un estudiante empobrecido que asesina a una anciana usurera con la esperanza de justificar moralmente su crimen. A medida que la trama avanza, se enfrenta a una lucha interna entre su culpabilidad y sus creencias filosóficas."))
        }
        //creamos una lista de novelas que se puedan utilizar en todas las activities del programa
    }
}
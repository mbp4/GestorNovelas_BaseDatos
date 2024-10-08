# GestorNovelas_BaseDatos
 
link al repositorio: https://github.com/mbp4/GestorNovelas_BaseDatos.git

En el ejercicio propuesto se nos pide hacer una aplicación de gestion de novelas, donde se puedan ver, eliminar o añadir nuevas novelas a una lista ya existente.

## EXPLICACIÓN

### Pantalla de inicio 

Para realizar la aplicacion necesitamos una aplicacion inicial que nos servirá de pantalla de inicio, su pseudocódigo sería:

```
Clase MainActivity extiende ComponentActivity:

    Declarar btnAlta como botón
    Declarar btnAcercaDe como botón
    Declarar recyclerNovelas como RecyclerView
    Declarar novelasAdapter como NovelasAdapter
    Declarar listadoNovelasF como una lista mutable de Novela
    Declarar db como FirebaseFirestore (base de datos)

    Constante ACCION_VER = 1
    Constante ACCION_BORRAR = 2

    Método onCreate(bundle):
        Llamar a super.onCreate(bundle)
        Establecer la vista de la actividad en el layout activity_main

        Asignar btnAlta al identificador btnAlta del layout
        Asignar btnAcercaDe al identificador btnAcercaDe del layout
        Asignar recyclerNovelas al identificador recyclerNovelas del layout

        Cuando se haga clic en btnAlta:
            Crear un intent para abrir NuevaNovelaActivity
            Iniciar la actividad con el intent

        Cuando se haga clic en btnAcercaDe:
            Crear un intent para abrir AcercaDeActivity
            Iniciar la actividad con el intent

        Llamar a mostrarNovelas()

    Método onResume():
        Llamar a super.onResume()
        Llamar a mostrarNovelas()

    Método privado mostrarNovelas():
        Consultar la colección "novelas" de la base de datos:
            Si se obtienen documentos correctamente:
                Limpiar listadoNovelasF
                Para cada documento en los documentos obtenidos:
                    Convertir el documento en un objeto Novela
                    Añadir la novela a listadoNovelasF
                Llamar a prepararRecyclerView()
            Si ocurre un error:
                Mostrar un mensaje de error al usuario
                Registrar el error en el log (logcat)

    Método privado prepararRecyclerView():
        Establecer recyclerNovelas.layoutManager como LinearLayoutManager

        Asignar novelasAdapter como NovelasAdapter(listadoNovelasF) con:
            Si la acción es ACCION_VER:
                Llamar a verNovela(novela seleccionada)
            Si la acción es ACCION_BORRAR:
                Llamar a borrarNovela(novela seleccionada)

        Asignar recyclerNovelas.adapter a novelasAdapter

        Llamar a novelasAdapter.notifyDataSetChanged()

    Método privado verNovela(novela):
        Crear un intent para abrir VerNovelaActivity
        Añadir los datos de la novela (título, autor, año, sinopsis) al intent
        Iniciar la actividad con el intent

    Método privado borrarNovela(novela):
        Consultar en la colección "novelas" donde el título sea igual al título de la novela:
            Si se obtienen documentos:
                Para cada documento en los documentos:
                    Borrar el documento de la base de datos
                Llamar a mostrarNovelas()
                Mostrar mensaje "Novela eliminada"
            Si ocurre un error:
                Mostrar mensaje "Error al borrar la novela"

```

En esta activity tendremos un TextView inicial con el titulo de la aplicacion, después encontramos dos botones: 

 -> Botón añadir novela: este botón se encargara de redirigir al usuario a una nueva pantalla donde podrá añadir todos los datos de una novela y añadir la misma a la base de datos de           novelas ya existente.
 
 -> Botón acerca de: este botón lleva al usuario a una pantalla donde podrá ver el autor de la aplicación

Después de los botones nos encontramos la lista de novelas existente donde solo se muestra el autor y el título de la obra, esta lista tiene dos botones: 

 -> Botón ver: este botón redirige al usuario a una nueva pantalla donde se muestra toda la informacion de la novela elegida.
 
 -> Botón eliminar: este botón eliminará de la base de datos la novela elegida.

 ### Novela

 Con esta clase creamos un obejto novela del cual más adelante crearemos una lista: 

 ```
Clase de datos Novela con los siguientes atributos:
    - titulo como String
    - autor como String
    - año como Int
    - sinopsis como String

    Constructorn():
        Llamar al constructor principal con valores por defecto:
            titulo vacío, autor vacío, año igual a 0, sinopsis vacía
```

### Base de datos de Novelas

Para esto haremos uso de Firebase, con esto podremos hacer uso de una base de datos donde se modificarán las novelas, es decir, se añadiran o se eliminarán de esta.

Visualmente uno de los elementos se vería así: 

<img width="1145" alt="Captura de pantalla 2024-10-08 a las 12 10 29" src="https://github.com/user-attachments/assets/b95ccd0f-3be7-4b64-9b46-d6d999623247">

### Adaptador para las Novelas

Con esta activity buscamos mostrar la lista que se ha creado de novelas, esta activity es necesaria ya que es la que hace posible que se muestre el recyclerView (el utilizado para poder crear la lista de novelas): 

```
Clase NovelasAdapter con dos parámetros:
    novelas: Lista mutable de objetos de tipo Novela
    onNovelasClick: Función que recibe una Novela y una acción (entero) y no devuelve valor

    La clase extiende RecyclerView.Adapter con un ViewHolder interno NovelasViewHolder

    Clase interna NovelasViewHolder con parámetro itemView:
        textTituloNovel: TextView que representa el título de la novela
        textAutorNovel: TextView que representa el autor de la novela
        btnVer: Botón para ver los detalles de la novela
        btnBorrar: Botón para borrar la novela

    Método onCreateViewHolder(parent, viewType) devuelve NovelasViewHolder:
        Crear una nueva vista inflando el layout item_novela
        Devolver un nuevo NovelasViewHolder con la vista inflada

    Método onBindViewHolder(holder, posición):
        Obtener la novela actual en la posición dada
        Asignar el título y autor de la novela actual a los TextViews correspondientes (textTituloNovel, textAutorNovel)
        
        Evento clic en btnVer:
            Llamar a onNovelasClick con la novela actual y la acción ACCION_VER

        Evento clic en btnBorrar:
            Mostrar un diálogo de confirmación con el título de la novela
            Si el usuario confirma (presiona "Sí"), llamar a onNovelasClick con la novela actual y la acción ACCION_BORRAR
            Si el usuario cancela (presiona "No"), no hacer nada

    Método getItemCount() devuelve entero:
        Retornar el tamaño de la lista de novelas

```

Este adaptador se encarga de la gestión de la visualización de las novelas y los botones. 

Estos 2 botones se encargan de llevar al usuario a una pantalla encargada de mostrar toda la información de la novela elegida por el usuario (btnVer) y de eliminar la novela que el usuario decida (btnBorrar).

Por otra parte el adaptador se encarga también de mostrar el tamaño de la lista.

### Actividad para mostrar toda la informacion de la Novela

En esta activity se mostrará toda la información de la novela pulsada:

```
Clase VerNovelaActivity extiende ComponentActivity:

    Atributos:
        txt1: TextView para mostrar el título de la novela
        txt2: TextView para mostrar el autor de la novela
        txt3: TextView para mostrar la sinopsis de la novela
        txt4: TextView para mostrar el año de la novela
        btnVolver: Button para regresar a la actividad anterior

    Método onCreate(Bundle):
        Llamar al método super.onCreate y establecer el layout 'activity_ver'

        Asignar txt1 con el TextView correspondiente (R.id.txt1)
        Obtener el título de la novela desde el intent y asignarlo a txt1

        Asignar txt2 con el TextView correspondiente (R.id.txt2)
        Obtener el autor de la novela desde el intent y asignarlo a txt2

        Asignar txt3 con el TextView correspondiente (R.id.txt3)
        Obtener la sinopsis de la novela desde el intent y asignarlo a txt3

        Asignar txt4 con el TextView correspondiente (R.id.txt4)
        Obtener el año de la novela desde el intent y convertirlo a cadena, luego asignarlo a txt4

        Asignar btnVolver con el Button correspondiente (R.id.btnVolver)
        Configurar el botón btnVolver para finalizar la actividad al hacer clic (volver a la actividad anterior)

```

En esta activity nos encontramos con varias variables que van asociadas a las ya definidas en los layouts, estas se encargarán de mostrar cada uno de los atributos de la novela que se ha pulsado.

Por otra parte el btnVolver también se encuentra asociado a uno ya definido en el layout, este se encarga de devolver al usuario a la pantalla principal.

### Nueva Novela

En esta activity se añade la informacion necesaria para que le usuario tenga la opción de poder añadir las novelas que quiera a la lista de novelas: 

```
Clase NuevaNovelaActivity extiende ComponentActivity:

    Declarar btnGuardarNovela como botón
    Declarar btnCancelar como botón
    Declarar editTitulo como EditText (campo de texto para el título)
    Declarar editAutor como EditText (campo de texto para el autor)
    Declarar editAño como EditText (campo de texto para el año)
    Declarar editSinopsis como EditText (campo de texto para la sinopsis)
    Declarar db como FirebaseFirestore (base de datos)

    Método onCreate(bundle):
        Llamar a super.onCreate(bundle)
        Asignar el layout de la actividad como activity_nueva_novela

        Asignar editTitulo, editAutor, editAño, editSinopsis a los identificadores correspondientes del layout
        Asignar btnGuardarNovela y btnCancelar a sus identificadores del layout

        Cuando se haga clic en btnGuardarNovela:
            Llamar a guardarNovela()
            Finalizar la actividad

        Cuando se haga clic en btnCancelar:
            Finalizar la actividad

    Método guardarNovela():
        Obtener el texto de editTitulo, convertirlo a cadena
        Obtener el texto de editAutor, convertirlo a cadena
        Obtener el texto de editAño, convertirlo a entero
        Obtener el texto de editSinopsis, convertirlo a cadena
        Crear un objeto Novela con los valores obtenidos

        En la colección "novelas" de la base de datos:
            Agregar nuevaNovela
            Si se guarda exitosamente:
                Mostrar mensaje "Novela guardada: título de la novela"
                Finalizar la actividad
            Si ocurre un error al guardar:
                Mostrar mensaje de error con el motivo del fallo

```

En este activity nos encontraremos varios TextEdit en los cuales el usuario podrá añadir la información necesaria para crear una nueva novela, después encontraremos un botón encargado de guardar la nueva novela y añadirla a la lista y otro botón que cancelará la operación y devuelve al usuario a la pantalla inicial. 

## Proceso de desarrollo

Para realizar la aplicacion se ha hecho uso del anterior proyecto como base y este ha sido modificado.

En el proyecto se nos solicitaba que nuestra aplicación de gestor de novelas contase con una base de datos que guardara estas, por lo tanto los cambios han sido: 

 -> Lo primero será cambiar la lista de novelas por una base de datos que creamos desde firebase con todos los elementos necesarios. 
 
 -> Después debemos cambiar los botones correspondientes:
 
    -> Botón borrar: debemos hacer que el botón borre la novela de la base de datos, haciendo que los datos ya no esten en esta, ademas de esto se añade un pop up que confirma que el              usuario desea borrar la novela de la base de datos. Cuando esta se borre aparecerá un mensaje que indicará al usuario si se ha borrado o no.

    -> Botón guardar: debemos hacer que el botón guarde la novela en la base de datos, haciendo que los datos se actualicen, cuando esta se añada aparecerá un mensaje que indicará al              usuario si se ha añadido o no.

 -> Y por último para no tener informacion innecesaria en nuestro programa borraremos el repositorio de novelas que se tenía en la anterior práctica.

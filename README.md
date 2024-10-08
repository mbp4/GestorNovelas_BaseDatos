# GestorNovelas_BaseDatos
 
link al repositorio: https://github.com/mbp4/GestorNovelas_BaseDatos.git

En el ejercicio propuesto se nos pide hacer una aplicación de gestion de novelas, donde se puedan ver, eliminar o añadir nuevas novelas a una lista ya existente.

## EXPLICACIÓN

### Pantalla de inicio 

Para realizar la aplicacion necesitamos una aplicacion inicial que nos servirá de pantalla de inicio, su pseudocódigo sería:

```
Clase MainActivity extiende ComponentActivity:
    Declarar las variables btnAlta, btnAcercaDe, recyclerNovelas, novelasAdapter
    Crear una lista mutable listadoNovelas con las novelas del repositorio

    Método onCreate(Bundle):
        Llamar al método super.onCreate y establecer la vista de la actividad con 'activity_main'

        Asignar btnAlta con el botón del layout correspondiente (R.id.btnAlta)
        Asignar btnAcercaDe con el botón del layout correspondiente (R.id.btnAcercaDe)

        Al hacer clic en btnAlta:
            Crear un Intent que apunta a la actividad NuevaNovelaActivity
            Iniciar actividad con resultado (REQUEST_CODE_ALTA_NOVELA)

        Al hacer clic en btnAcercaDe:
            Crear un Intent que apunta a la actividad AcercaDeActivity
            Iniciar la actividad

        Crear el adaptador novelasAdapter con la lista de novelas y una acción que ocurre cuando se selecciona una novela:
            Crear un Intent que apunta a la actividad VerNovelaActivity
            Pasar los detalles de la novela (título, autor, año, sinopsis) al Intent
            Iniciar la actividad

        Asignar recyclerNovelas con el RecyclerView del layout correspondiente (R.id.recyclerNovelas)
        Asignar el adaptador novelasAdapter al RecyclerView
        Establecer el diseño de RecyclerView con LinearLayoutManager

    Método onActivityResult(requestCode, resultCode, data):
        Si requestCode es REQUEST_CODE_ALTA_NOVELA y resultCode es RESULT_OK:
            Notificar al adaptador de novelas que los datos han cambiado (notifyDataSetChanged)

    Objeto companion:
        Definir constante REQUEST_CODE_ALTA_NOVELA con valor 1

```

En esta activity tendremos un TextView inicial con el titulo de la aplicacion, después encontramos dos botones: 

 -> Botón añadir novela: este botón se encargara de redirigir al usuario a una nueva pantalla donde podrá añadir todos los datos de una novela y añadir la misma a la lista de novelas ya         existente.
 -> Botón acerca de: este botón lleva al usuario a una pantalla donde podrá ver el autor de la aplicación

Después de los botones nos encontramos la lista de novelas existente donde solo se muestra el autor y el título de la obra, esta lista tiene dos botones: 

 -> Botón ver: este botón redirige al usuario a una nueva pantalla donde se muestra toda la informacion de la novela elegida.
 
 -> Botón eliminar: este botón eliminará de la lista la novela elegida.

 ### Novela

 Con esta clase creamos un obejto novela del cual más adelante crearemos una lista: 

 ```
Clase de datos Novela:
    Atributos:
        - titulo: String
        - autor: String
        - año: Int
        - sinopsis: String

```

### Repositorio de Novelas

En esta clase creamos una lista haciendo uso del companion object, el cual nos ayuda a que esta lista sea accesible para todas las activities del proyecto: 

```
Clase NovelasRepository:
    Objeto companion:
        Crear una lista mutable de objetos Novela llamada 'novelas'

        Inicializar la lista 'novelas' con varias novelas:
            Añadir Novela("La llamada de Cthulhu", "H. P. Lovecraft", 1928, "gyugdef")
            Añadir Novela("La llamada de Pepito", "Manuel", 1948, "")
            Añadir Novela("La llamada de Manuel", "Jose", 1932, "")
            Añadir Novela("La llamada de Juan", "Anonimo", 1928, "")
            Añadir Novela("La llamada de Pedro", "Maria", 1976, "")

```

### Adaptador para las Novelas

Con esta activity buscamos mostrar la lista que se ha creado de novelas, esta activity es necesaria ya que es la que hace posible que se muestre el recyclerView (el utilizado para poder crear la lista de novelas): 

```
Clase NovelasAdapter extiende RecyclerView.Adapter:

    Atributos:
        novelas: lista mutable de objetos Novela
        onNovelasClick: función que se ejecuta al hacer clic en una novela

    Clase interna NovelasViewHolder extiende RecyclerView.ViewHolder:
        Atributos:
            textTituloNovel: TextView para mostrar el título de la novela
            textAutorNovel: TextView para mostrar el autor de la novela
            btnVer: Button para ver la información completa de la novela
            btnBorrar: Button para borrar la novela de la lista

    Método onCreateViewHolder(parent, viewType):
        Inflar la vista de la novela desde el layout 'item_novela'
        Devolver un objeto NovelasViewHolder con la vista inflada

    Método onBindViewHolder(holder, position):
        Obtener la novela en la posición actual de la lista
        Asignar el título y autor de la novela a los respectivos TextView en 'holder'
        Asignar una acción al botón btnVer:
            Llamar a la función onNovelasClick con la novela seleccionada
        Asignar una acción al botón btnBorrar:
            Eliminar la novela de la lista en la posición actual
            Notificar que los datos han cambiado (notifyDataSetChanged)

    Método getItemCount():
        Devolver el número total de novelas en la lista
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

    Atributos:
        btnGuardarNovela: Button para guardar la nueva novela
        btnCancelar: Button para cancelar la creación de una nueva novela
        editTitulo: EditText para ingresar el título de la novela
        editAutor: EditText para ingresar el autor de la novela
        editAño: EditText para ingresar el año de la novela
        editSinopsis: EditText para ingresar la sinopsis de la novela
        listadoNovelas: Lista mutable de objetos Novela del repositorio

    Método onCreate(Bundle):
        Llamar al método super.onCreate y establecer el layout 'activity_nueva_novela'

        Asignar los campos de edición (editTitulo, editAutor, editAño, editSinopsis) con los elementos correspondientes del layout

        Asignar los botones (btnGuardarNovela y btnCancelar) con los botones del layout

        Configurar btnGuardarNovela para que al hacer clic:
            Llamar al método guardarNovela
            Finalizar la actividad

        Configurar btnCancelar para que al hacer clic:
            Finalizar la actividad (sin guardar cambios)

    Método guardarNovela():
        Obtener el texto de los campos de edición (editTitulo, editAutor, editAño, editSinopsis)
        Convertir el año de texto a entero
        Crear una nueva instancia de Novela con los valores obtenidos
        Añadir la nueva novela a la lista 'listadoNovelas'

        Crear un Intent vacío (resultIntent)
        Establecer el resultado de la actividad como RESULT_OK
        Finalizar el método
```

En este activity nos encontraremos varios TextEdit en los cuales el usuario podrá añadir la información necesaria para crear una nueva novela, después encontraremos un botón encargado de guardar la nueva novela y añadirla a la lista y otro botón que cancelará la operación y devuelve al usuario a la pantalla inicial. 

## Proceso de desarrollo

Al realizar la aplicación necesitamos una lista para que se guarden las novelas que el usuario ha querido meter en la aplicación y que este tenga algunas para que las pueda visualizar, para esto utilizaremos un recyclerView (un componente de AndroidStudio que permite utilizar listas de varios elementos de manera eficiente), pero este elemento no permite visualizar la vista por lo que necesitamos un elemento adaptador que se encargue de gestionar la vista de cada una de las novelas y actualizarse en el caso necesario. 

Lo primero que se nos pide para realizar la aplicacion era el uso de Layouts, por lo tanto lo primero que se ha realizado han sido los correspondientes Layout de cada una de las activities, por lo que en cada pantalla necesitaremos asociar los elementos de la pantalla a cada uno de los ya existentes en el Layout, ademas de asociar a cada activity su Layout correspondiente. 

Por último, en la aplicacion ha sido necesario crear un Companion Object el cual es necesario para que las pantallas puedan acceder a un objeto en común sin problema, en este caso era el repositorio de novelas ya que la lista era necesaria en varias activities para poder hacer el programa funcional.

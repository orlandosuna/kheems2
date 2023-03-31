package mx.itson.kheems.entidades

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import mx.itson.kheems.persistencia.GanadorDB

class Ganador {
    // Atributos
    var id: Int = 0
    var nombre: String? = null
    var puntos: Int = 0
    var oportunidades: Int = 0

    // Constructor vacío
    constructor()

    // Constructor con parámetros
    constructor(id: Int, nombre: String?, puntos: Int, oportunidades: Int) {
        this.id = id
        this.nombre = nombre
        this.puntos = puntos
        this.oportunidades = oportunidades
    }

    // Sirve para guardar un nuevo ganador en la base de datos
    fun guardar(context: Context, nombre: String, puntos: Int, oportunidades: Int) {
        try {
            // Se obtiene la base de datos
            val db = GanadorDB(context, "GanadorDB", null, 1)
            val baseDatos: SQLiteDatabase = db.writableDatabase

            // Creamos un ContentValues para agregar los valores a insertar
            val valores = ContentValues()
            valores.put("nombre", nombre)
            valores.put("puntos", puntos)
            valores.put("oportunidades", oportunidades)

            // Insertamos los valores en la tabla ganador
            baseDatos.insert("ganador", null, valores)

        } catch (ex: Exception) {
            // Se presenta la excepción
            Log.e("ocurrio un error al guardar ganador", ex.toString())
        }
    }

    // Función para obtener todos los ganadores de la base de datos
    fun obtener(context: Context): ArrayList<Ganador> {


        val ganadores: MutableList<Ganador> = ArrayList()
        try {

            // Se obtiene la DB
            val db = GanadorDB(context, "GanadorDB", null, 1)
            val baseDatos: SQLiteDatabase = db.writableDatabase

            // Realizamos una consulta para obtener a todos los ganadores
            val cursor = baseDatos.rawQuery("SELECT * FROM ganador", null)

            // Iteramos sobre los resultados obtenidos y los agregamos a la lista de ganadores
            while (cursor.moveToNext()) {
                val ganador = Ganador()
                ganador.id = cursor.getInt(0)
                ganador.nombre = cursor.getString(1)
                ganador.puntos = cursor.getInt(2)
                ganador.oportunidades = cursor.getInt(3)
                ganadores.add(ganador)
            }
        } catch (ex: Exception) {
            // Manejamos la excepción
            Log.e("ocurrio un error al obtener ganador", ex.toString())
        }

        // Regresamos la lista de ganadores como un ArrayList
        return ganadores as ArrayList<Ganador>
    }
}

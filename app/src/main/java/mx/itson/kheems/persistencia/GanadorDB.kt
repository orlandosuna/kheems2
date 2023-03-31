package mx.itson.kheems.persistencia

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// Clase que permite la conexión de la DB
class GanadorDB(
    context: Context?, //
    name: String?, //
    factory: SQLiteDatabase.CursorFactory?, //
    version: Int //
) : SQLiteOpenHelper(context, name, factory, version) {

    // Método que se ejecuta cuando se crea la base de datos
    override fun onCreate(p0: SQLiteDatabase?) {
        try {
            Log.i("DataBase", "Creando base de datos")
            // Se ejecuta una sentencia SQL que crea la tabla "Ganador" con sus columnas
            p0?.execSQL(
                "CREATE TABLE Ganador" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT, oportunidades INTEGER, puntos INTEGER)"
            )
        } catch (ex: Exception) {
            // En caso de error se muestra un mensaje
            Log.e("DataBase", "Error al crear la base de datos", ex)
        }
    }

    // Método que se ejecuta cuando se actualiza la base de datos
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}
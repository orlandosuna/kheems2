package mx.itson.kheems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import mx.itson.kheems.adapters.GanadorAdapter
import mx.itson.kheems.entidades.Ganador

class GanadorListActivity : AppCompatActivity() {
    var listaGanadores: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ganador_list)

        // Obtener referencia al ListView en el layout
        listaGanadores = findViewById(R.id.listGanador)
        // Cargar la lista de ganadores
        cargarLista()
    }

    // Cargar lista de ganadores
    fun cargarLista() {
        // Obtener los ganadores de la base de datos, ordenarlos por puntos y nombre y tomar los primeros 10
        val ganadores: ArrayList<Ganador> =
            Ganador().obtener(this).sortedWith(compareBy({ it.puntos }, { it.nombre }))
                .take(10) as ArrayList<Ganador>
        // Crear un adaptador personalizado para mostrar los ganadores en el ListView
        val adapter = GanadorAdapter(ganadores)
        // Asignar el adaptador al ListView
        listaGanadores?.adapter = adapter
    }
}

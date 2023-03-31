package mx.itson.kheems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import mx.itson.kheems.entidades.Ganador

class GanadorFormActivity : AppCompatActivity(), View.OnClickListener {
    // Variables para guardar los puntos y oportunidades recibidos desde la actividad anterior
    var puntos = 0
    var oportunidades = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ganador_form)

        // Recupera los puntos y oportunidades del Intent y los guarda
        puntos = intent.getIntExtra("puntos", 0)
        oportunidades = intent.getIntExtra("oportunidades", 0)

        // Asigna el listener para el botón de guardar
        findViewById<View>(R.id.btnGuardar).setOnClickListener(this)
    }

    // Función para guardar el nombre del ganador
    fun guardar() {

        val nombre = findViewById<EditText>(R.id.etNombre).text.toString()

        // Crea un objeto Ganador y llama su función guardar() para guardar el nombre del ganador en la base de datos
        Ganador().guardar(this, nombre, oportunidades, puntos)

        finish()
    }

    // Función para manejar el evento de clic en los botones
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnGuardar -> guardar()
        }
    }
}

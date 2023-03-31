package mx.itson.kheems

import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var ubicacion = 0 //Variable para guardar la ubicacion de la imagen objetivo
    var contador = 0 //Variable para llevar la cuenta de los intentos
    var puntos = 0 //Variable para acumular puntos del juego
    var oportunidades = 0 //Variable para llevar la cuenta de las veces que se ha jugado

    /*
* Se ejecuta al iniciar la actividad principal
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniciar()

        //Se agrega el OnClickListener a los botones
        val btnReiniciar = findViewById<View>(R.id.btnReiniciar) as Button
        btnReiniciar.setOnClickListener(this)

        val btnGanadores = findViewById<View>(R.id.btnResultados) as Button
        btnGanadores.setOnClickListener(this)

        //Se agrega el OnClickListener a las opciones de la partida
        for (i in 1..12) {
            val btnSeleccion = findViewById<View>(
                resources.getIdentifier(
                    "opcion$i", "id", this.packageName
                )
            ) as ImageButton
            btnSeleccion.setOnClickListener(this)
        }
    }

    /*
    * Funcion para inicializar la partida
    */
    fun iniciar() {
        //Se asigna la imagen de pregunta a cada opcion
        findViewById<View>(R.id.opcion1).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion2).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion3).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion4).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion5).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion6).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion7).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion8).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion9).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion10).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion11).setBackgroundResource(R.drawable.icon_pregunta)
        findViewById<View>(R.id.opcion12).setBackgroundResource(R.drawable.icon_pregunta)

        //Se habilitan todas las opciones
        for (i in 1..12) {
            val btnSeleccion =
                findViewById<ImageButton>(resources.getIdentifier("opcion$i", "id", packageName))
            btnSeleccion.isEnabled = true
        }

        //Checar si es la primera vez que se juega para evitar que se sume una oportunidad
        if (puntos == 0) {
            oportunidades = 1
        } else {
            oportunidades++
        }

        //Se acumulan los puntos y se reinicia el contador de intentos
        puntos += contador
        contador = 0

        //Se genera una ubicacion aleatoria para la imagen objetivo
        val random = Random()
        ubicacion = random.nextInt(12) + 1
        Log.e("ubicacion", ubicacion.toString())
    }

    /*
    * Funcion para destapar la imagen seleccionada
    */
    // Función que se llama para destapar una opción
    fun destapar(opcion: Int) {

        // Si la opción seleccionada es la correcta
        if (opcion == ubicacion) {

            // Vibrar y mostrar un mensaje de felicitación
            val vibrador = getSystemService(VIBRATOR_SERVICE) as Vibrator
            val patronVibracion = longArrayOf(0, 200, 150, 200, 150, 200)
            vibrador.vibrate(VibrationEffect.createWaveform(patronVibracion, -1))
            Toast.makeText(this, "¡PERMDISTE!", Toast.LENGTH_LONG).show()

            // Cambiar el fondo de todas las opciones, mostrando la imagen de Cheems llorando en la opción correcta
            for (i in 1..12) {
                val btnSeleccion = findViewById<View>(
                    resources.getIdentifier(
                        "opcion$i", "id", this.packageName
                    )
                ) as ImageButton
                if (i == opcion) {
                    btnSeleccion.setBackgroundResource(R.drawable.icon_cheems_llora)
                } else {
                    btnSeleccion.setBackgroundResource(R.drawable.icon_cheems)
                }
            }

        } else { // Si la opción seleccionada es incorrecta

            // Deshabilitar la opción seleccionada, cambiar su fondo y aumentar el contador de intentos
            val btnSeleccionado = findViewById<View>(
                resources.getIdentifier(
                    "opcion$opcion", "id", this.packageName
                )
            ) as ImageButton
            btnSeleccionado.setBackgroundResource(R.drawable.icon_cheems)
            btnSeleccionado.isEnabled = false
            contador++

            // Mostrar un mensaje de "sigue intentando" con el número de intentos actual
            Toast.makeText(this, "¡SIGUE INTENTANDO! $contador", Toast.LENGTH_LONG).show()

            // Si se han agotado los intentos (11) mostrar un mensaje de victoria, vibrar y mostrar la imagen de Cheems ganando en la opción correcta
            if (contador == 11) {
                val vibrador = getSystemService(VIBRATOR_SERVICE) as Vibrator
                vibrador.vibrate(
                    VibrationEffect.createOneShot(
                        150, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
                vibrador.vibrate(
                    VibrationEffect.createOneShot(
                        150, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
                Toast.makeText(this, "¡GANASTE!", Toast.LENGTH_LONG).show()

                val btnGanador = findViewById<View>(
                    resources.getIdentifier(
                        "opcion$ubicacion", "id", this.packageName
                    )
                ) as ImageButton
                btnGanador.setBackgroundResource(R.drawable.cheems_win)

                // Añadir los puntos obtenidos al contador total, crear un Intent para ir a la pantalla de resultados y reiniciar los puntos y oportunidades
                puntos += contador
                val intent = Intent(this, GanadorFormActivity::class.java)
                intent.putExtra("puntos", puntos)
                intent.putExtra("oportunidades", oportunidades)
                startActivity(intent)
                puntos = 0
                oportunidades = 1

            } else { // Si aún quedan intentos

                // Vibrar y cambiar el fondo de la opción seleccionada a la imagen de Cheems normal
                val vibrador = getSystemService(VIBRATOR_SERVICE) as Vibrator
                vibrador.vibrate(
                    VibrationEffect.createOneShot(
                        150, VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )

                // Se cambia el fondo de la opción seleccionada a la imagen de Cheems normal
                val btnSeleccion = findViewById<View>(
                    resources.getIdentifier(
                        "opcion$opcion", "id", this.packageName
                    )
                ) as ImageButton
                btnSeleccion.setBackgroundResource(R.drawable.icon_cheems)
            }
        }
    }

    // Método onClick para detectar cuando se hace click en una vista
    override fun onClick(view: View) {
        // Se evalúa el ID de la vista que recibió el clic
        if (view.id == R.id.opcion1) {
            destapar(1)
        } else if (view.id == R.id.opcion2) {
            destapar(2)
        } else if (view.id == R.id.opcion3) {
            destapar(3)
        } else if (view.id == R.id.opcion4) {
            destapar(4)
        } else if (view.id == R.id.opcion5) {
            destapar(5)
        } else if (view.id == R.id.opcion6) {
            destapar(6)
        } else if (view.id == R.id.opcion7) {
            destapar(7)
        } else if (view.id == R.id.opcion8) {
            destapar(8)
        } else if (view.id == R.id.opcion9) {
            destapar(9)
        } else if (view.id == R.id.opcion10) {
            destapar(10)
        } else if (view.id == R.id.opcion11) {
            destapar(11)
        } else if (view.id == R.id.opcion12) {
            destapar(12)
        } else if (view.id == R.id.btnReiniciar) {
            // Si se hizo clic en el botón Reiniciar, se llama al método iniciar()
            iniciar()
        } else if (view.id == R.id.btnResultados) {
            // Si se hizo clic en el botón Resultados, se crea un intent para lanzar la actividad GanadorListActivity
            val intent = Intent(this, GanadorListActivity::class.java)
            startActivity(intent)
        }
    }

    // Método onResume que se llama cuando la actividad vuelve a estar en primer plano
    override fun onResume() {
        super.onResume()
        // Se llama al método iniciar() para inicializar el juego
        iniciar()
    }

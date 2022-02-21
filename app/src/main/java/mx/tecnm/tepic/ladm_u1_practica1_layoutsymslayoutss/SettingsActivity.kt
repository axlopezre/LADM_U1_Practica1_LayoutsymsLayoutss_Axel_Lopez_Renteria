package mx.tecnm.tepic.ladm_u1_practica1_layoutsymslayoutss

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.settings_activity.*
import java.io.OutputStreamWriter
import java.lang.Exception

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listamenu.setOnItemClickListener { adapterView, view, posicion, l ->
            //Posición = el que contiene el índice cuando hago click
            when(posicion){
                0->{llamarPrimeraActivity()}
                1->{llamarSegundaActivity()
                }
            }
        }

        salir.setOnClickListener {
            finish()
        }

        guardar3.setOnClickListener {
            if(ValidaCampo(telefono) || ValidaCampo(nombre2)) return@setOnClickListener
            guardarArchivo()
        }
    }
    private fun guardarArchivo() {
        try {
            val archivo = OutputStreamWriter(openFileOutput("archivo.txt", MODE_PRIVATE))
            var cadena = nombre2.text.toString()+"&&"+
                    telefono.text.toString()

            archivo.write(cadena)
            archivo.flush()
            archivo.close()
            telefono.setText("")
            nombre2.setText("")

            AlertDialog.Builder(this).setMessage("SE GUARDARON LOS DATOS").show()
        }catch (e: Exception){
            AlertDialog.Builder(this).setMessage(e.message).show()
        }
    }

    fun llamarPrimeraActivity(){
        val Ventana1 = Intent(this, ScrollingActivity::class.java)
        startActivity(Ventana1)
    }

    fun llamarSegundaActivity(){
        val Ventana2 = Intent(this, FullscreenActivity::class.java)
        startActivity(Ventana2)
    }

    fun ValidaCampo(text: EditText): Boolean{
        if(telefono.text.isEmpty() || nombre2.text.isEmpty()){
            AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("RELLENE EL O LOS CAMPOS CORRESPONDIENTES")
                .setNeutralButton("ACEPTAR", {d,i->d.dismiss()})
                .show()
            return true
        }
        return false
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}
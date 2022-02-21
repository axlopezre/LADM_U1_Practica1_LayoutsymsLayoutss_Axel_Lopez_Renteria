package mx.tecnm.tepic.ladm_u1_practica1_layoutsymslayoutss

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_scrolling.*
import mx.tecnm.tepic.ladm_u1_practica1_layoutsymslayoutss.databinding.ActivityScrollingBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    var vector = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        binding.fab.setOnClickListener {
            finish()
        }

        insertar.setOnClickListener {
            if(ValidaCampo(valor)) return@setOnClickListener
            else{
                vector.add(valor.text.toString())
                valor.setText("")
                mostrarlista()
            }
        }
        insertarobservaciones.setOnClickListener {
            if(ValidaCampo2(valor2)) return@setOnClickListener
            guardarDatos()
        }
        leer.setOnClickListener {
            leerDatos()
        }

    }
    private fun mostrarlista(){
        lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

        lista.setOnItemClickListener { adapterView, view, position, id ->
            val posición = position
            AlertDialog.Builder(this)
                .setTitle("ATENCIÓN")
                .setMessage("ESTAS SEGURO QUE DESEAS BORRAR INDICE")
                .setPositiveButton("OK", {d, i->
                    vector.removeAt(posición)
                    mostrarlista()
                    d.dismiss()
                })
                .setNeutralButton("CANCEL", {d, i->d.cancel()})
                .show()
        }
    }

    private fun guardarDatos(){
        try {
            val datos= OutputStreamWriter(openFileOutput("archivo.txt", MODE_PRIVATE))
            var cadena= valor2.text.toString()+"&&"
            datos.write(cadena)
            datos.flush()
            datos.close()
            valor2.setText("")

            AlertDialog.Builder(this).setMessage("Se enviaron los datos").show()
        }catch (e:Exception){
            AlertDialog.Builder(this).setMessage(e.message).show()
        }
    }

    fun ValidaCampo(text: EditText): Boolean{
        if(valor.text.isEmpty()){
            AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("RELLENE EL CAMPO CORRESPONDIENTE")
                .setNeutralButton("ACEPTAR", {d,i->d.dismiss()})
                .show()
            return true
        }
        return false
    }
    fun ValidaCampo2(text: EditText): Boolean{
        if(valor2.text.isEmpty()){
            AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("RELLENE EL CAMPO CORRESPONDIENTE")
                .setNeutralButton("ACEPTAR", {d,i->d.dismiss()})
                .show()
            return true
        }
        return false
    }

    private fun leerDatos(){
        try {
            val archivo= BufferedReader(InputStreamReader(openFileInput("archivo.txt")))
            var cadena =archivo.readLine()

            cadena = cadena.replace("&&", "\n")
            archivo.close()

            AlertDialog.Builder(this).setMessage(cadena).show()
        }catch (e:Exception){
            AlertDialog.Builder(this).setMessage(e.message).show()
        }
    }
}
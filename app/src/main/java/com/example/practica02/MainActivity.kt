package com.example.practica02

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
private lateinit var tvMensaje: TextView
private lateinit var etxtNombre: EditText
private lateinit var etxtMatricula: EditText
private lateinit var btnRegistrar: Button
private lateinit var btnLimpiar: Button
private lateinit var pbCarga: ProgressBar
private lateinit var btnTemaYSV: Button
private var contadorAsistentes = 0
private var oscuroYSV = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }

        tvMensaje = findViewById(R.id.tvMensaje)
        etxtNombre = findViewById(R.id.etxtNombre)
        etxtMatricula = findViewById(R.id.etxtMatricula)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        pbCarga = findViewById(R.id.pbCarga)
        btnTemaYSV = findViewById(R.id.btnTemaYSV)

        val currentNightMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        oscuroYSV = (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES)
        if (oscuroYSV) {
            btnTemaYSV.text = getString(R.string.btn_tema_claroYSV)
        } else {
            btnTemaYSV.text = getString(R.string.btn_tema_oscuroYSV)
        }

        btnRegistrar.setOnClickListener {
            registrarse()
        }
        btnLimpiar.setOnClickListener { limpiar() }

        btnTemaYSV.setOnClickListener {
            cambiarTemaYSV()
        }

    }

    private fun registrarse(){
        val nombre = etxtNombre.text.toString().trim()
        val matricula = etxtMatricula.text.toString().trim()

        if (matricula.isEmpty()){
            Toast.makeText(this, getString(R.string.val_matricula_vaciaYSV), Toast.LENGTH_SHORT).show()
            return
        }else if (matricula.length !=7) {
            Toast.makeText(this, getString(R.string.val_matricula_largoYSV), Toast.LENGTH_SHORT).show()
            return
        }

        if (nombre.isEmpty()){
            Toast.makeText(this, getString(R.string.val_nombre_vacioYSV), Toast.LENGTH_SHORT).show()
            return
        }else if (nombre.length <3) {
            Toast.makeText(this, getString(R.string.val_nombre_cortoYSV), Toast.LENGTH_SHORT).show()
            return
        }


        pbCarga.visibility = View.VISIBLE
        tvMensaje.text = ""
        btnRegistrar.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            pbCarga.visibility = View.GONE
            contadorAsistentes++
            tvMensaje.text = getString(R.string.msg_registradoYSV, nombre, matricula, contadorAsistentes)

            etxtNombre.setText("")
            etxtMatricula.setText("")
            etxtNombre.requestFocus()
            btnRegistrar.isEnabled = true
        }, 1500)
    }

    private fun limpiar (){
        etxtNombre.setText("")
        etxtMatricula.setText("")
        pbCarga.visibility = View.GONE
        tvMensaje.text = getString(R.string.msg_inicialYSV)
        btnRegistrar.text = getString(R.string.btn_registrarYSV)
        btnRegistrar.text = getString(R.string.btn_registrar_otroYSV)
        btnRegistrar.isEnabled = true
        etxtNombre.requestFocus()
    }

    private fun cambiarTemaYSV(){
        oscuroYSV = !oscuroYSV

        if(oscuroYSV) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            btnTemaYSV.text = getString(R.string.btn_tema_claroYSV)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            btnTemaYSV.text = getString(R.string.btn_tema_oscuroYSV)
        }
    }
}

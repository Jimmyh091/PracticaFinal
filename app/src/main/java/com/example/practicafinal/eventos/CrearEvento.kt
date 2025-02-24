package com.example.practicafinal.eventos

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicafinal.Perfil
import com.example.practicafinal.menu.Menu
import com.example.practicafinal.R
import com.example.practicafinal.databinding.ActivityCrearEventoBinding
import com.google.firebase.database.FirebaseDatabase

class CrearEvento : AppCompatActivity() {

    private lateinit var binding: ActivityCrearEventoBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityCrearEventoBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.crearEventoBannerAtras.setOnClickListener {
            finish()
        }

        binding.crearEventoBannerLogo.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        binding.crearEventoBannerFotoPerfil.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }

        binding.crearEventoFoto.setOnClickListener {
            /*
            COGER FOTO DEL MOVIL
            */
        }

        binding.crearEventoTietFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val año = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val día = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                binding.crearEventoTietFecha.setText(fechaSeleccionada)
            }, año, mes, día)

            datePicker.show()
        }

        binding.crearEventoBotonCrear.setOnClickListener {

            /* BD
            COGER EVENTOS
            */

            val listaEventos = listOf(Evento())

            if (
                binding.crearEventoTietNombre.text.toString().isEmpty() ||
                binding.crearEventoTietDescripcion.text.toString().isEmpty() ||
                binding.crearEventoTietFecha.text.toString().isEmpty() ||
                binding.crearEventoTietAforo.text.toString().isEmpty() ||
                binding.crearEventoTietPrecio.text.toString().isEmpty()
            ){
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }else{

                var valido = true

                for (evento in listaEventos) {

                    if (evento.nombre == binding.crearEventoTietNombre.text.toString() || evento.fecha == binding.crearEventoTietFecha.text.toString()) {
                        valido = false
                        break
                    }
                }

                if (valido) {

                    var evento: Evento? = null

                    val nombre = binding.crearEventoTietNombre.text.toString()

                    isNombreEventoDisponible(nombre) { disponible ->
                        if (disponible) {
                            val database = FirebaseDatabase.getInstance().getReference("tienda").child("eventos")
                            val nuevoEventoId = database.push().key ?: return@isNombreEventoDisponible

                            evento = Evento(
                                nuevoEventoId,
                                binding.crearEventoTietNombre.text.toString(),
                                binding.crearEventoTietDescripcion.text.toString(),
                                binding.crearEventoTietFecha.text.toString(),
                                binding.crearEventoTietPrecio.text.toString().toFloat(),
                                binding.crearEventoTietAforo.text.toString().toInt()
                            )

                            val nuevoEvento = Evento(nombre)
                            database.child(nuevoEventoId).setValue(nuevoEvento)
                            Toast.makeText(this, "Evento creado correctamente", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Ya existe un evento con ese nombre", Toast.LENGTH_SHORT).show()
                        }
                    }

                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(this, "Ya existe un evento con ese nombre o en esa fecha", Toast.LENGTH_LONG).show()
                }

            }

        }
    }

    fun isNombreEventoDisponible(nombre: String, onResult: (Boolean) -> Unit) {
        val database = FirebaseDatabase.getInstance().getReference("tienda").child("eventos")

        database.get().addOnSuccessListener { snapshot ->
            var disponible = true
            for (eventoSnapshot in snapshot.children) {
                val nombreExistente = eventoSnapshot.child("nombre").getValue(String::class.java)
                if (nombreExistente != null && nombreExistente.equals(nombre, ignoreCase = true)) {
                    disponible = false
                    break
                }
            }
            onResult(disponible)
        }.addOnFailureListener {
            onResult(false) // En caso de error, asumimos que no está disponible
        }
    }

}
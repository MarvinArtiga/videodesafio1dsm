package com.example.desafio1

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etMontoTotal: EditText
    private lateinit var etNumeroPersonas: EditText
    private lateinit var rgPropina: RadioGroup
    private lateinit var switchIVA: Switch
    private lateinit var btnCalcular: Button
    private lateinit var btnLimpiar: Button
    private lateinit var tvResultado: TextView
    private lateinit var etOtroPorcentaje: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etMontoTotal = findViewById(R.id.etMontoTotal)
        etNumeroPersonas = findViewById(R.id.etNumeroPersonas)
        rgPropina = findViewById(R.id.rgPropina)
        switchIVA = findViewById(R.id.switchIVA)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        tvResultado = findViewById(R.id.tvResultado)
        etOtroPorcentaje = findViewById(R.id.etOtroPorcentaje)

        btnCalcular.setOnClickListener {
            calcularPropina()
        }

        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }

        rgPropina.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbOtro) {
                etOtroPorcentaje.visibility = View.VISIBLE
            } else {
                etOtroPorcentaje.visibility = View.GONE
                etOtroPorcentaje.text.clear()
            }
        }
    }

    private fun calcularPropina() {
        val montoStr = etMontoTotal.text.toString()
        val personasStr = etNumeroPersonas.text.toString()

        if (montoStr.isEmpty() || personasStr.isEmpty()) {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val monto = montoStr.toDouble()
        val personas = personasStr.toInt()

        if (monto == 0.0) {
            Toast.makeText(this, "Comiste gratis, no hay nada que calcular mano", Toast.LENGTH_SHORT).show()
            return
        }
        if (personas == 0) {
            Toast.makeText(this, "¿Cero personas? ¿Y quién come pues?", Toast.LENGTH_SHORT).show()
            return
        }

        val propinaPorcentaje = when (rgPropina.checkedRadioButtonId) {
            R.id.rb10 -> 0.10
            R.id.rb15 -> 0.15
            R.id.rb20 -> 0.20
            R.id.rbOtro -> {
                val otroStr = etOtroPorcentaje.text.toString()
                if (otroStr.isEmpty()) {
                    Toast.makeText(this, "Ingresá un porcentaje personalizado", Toast.LENGTH_SHORT).show()
                    return
                }
                val otroValor = otroStr.toDoubleOrNull()
                if (otroValor == null || otroValor < 0) {
                    Toast.makeText(this, "Porcentaje inválido", Toast.LENGTH_SHORT).show()
                    return
                }
                otroValor / 100
            }
            else -> 0.0
        }

        var total = monto + (monto * propinaPorcentaje)

        if (switchIVA.isChecked) {
            total += monto * 0.16
        }

        val totalPorPersona = total / personas

        tvResultado.text = "Total: \$%.2f\nCada quien paga: \$%.2f".format(total, totalPorPersona)
    }

    private fun limpiarCampos() {
        etMontoTotal.text.clear()
        etNumeroPersonas.text.clear()
        rgPropina.clearCheck()
        switchIVA.isChecked = false
        etOtroPorcentaje.text.clear()
        etOtroPorcentaje.visibility = View.GONE
        tvResultado.text = ""
    }
}

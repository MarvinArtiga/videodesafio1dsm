package com.example.desafio1

import android.os.Bundle
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

        btnCalcular.setOnClickListener {
            calcularPropina()
        }

        btnLimpiar.setOnClickListener {
            limpiarCampos()
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

        if (personas == 0) {
            Toast.makeText(this, "¿Cero personas? ¿Y quién come pues?", Toast.LENGTH_SHORT).show()
            return
        }

        val propinaPorcentaje = when (rgPropina.checkedRadioButtonId) {
            R.id.rb10 -> 0.10
            R.id.rb15 -> 0.15
            R.id.rb20 -> 0.20
            R.id.rbOtro -> 0.18  // valor por defecto si eligen "Otro"
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
        tvResultado.text = ""
    }
}
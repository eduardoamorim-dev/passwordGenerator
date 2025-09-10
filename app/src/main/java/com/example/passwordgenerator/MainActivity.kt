package com.example.passwordgenerator

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBarLength = findViewById<SeekBar>(R.id.seekBarLength)
        val tvLength = findViewById<TextView>(R.id.tvLength)
        val cbUppercase = findViewById<CheckBox>(R.id.cbUppercase)
        val cbLowercase = findViewById<CheckBox>(R.id.cbLowercase)
        val cbNumbers = findViewById<CheckBox>(R.id.cbNumbers)
        val cbSymbols = findViewById<CheckBox>(R.id.cbSymbols)
        val btnGenerate = findViewById<Button>(R.id.btnGenerate)
        val tvPassword = findViewById<TextView>(R.id.tvPassword)
        val btnCopy = findViewById<Button>(R.id.btnCopy)

        seekBarLength.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val length = if (progress < 4) 4 else progress
                tvLength.text = "$length caracteres"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        btnGenerate.setOnClickListener {
            if (!cbUppercase.isChecked && !cbLowercase.isChecked &&
                !cbNumbers.isChecked && !cbSymbols.isChecked) {
                Toast.makeText(this, "Selecione pelo menos uma opcao!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val length = if (seekBarLength.progress < 4) 4 else seekBarLength.progress
            var characters = ""

            if (cbUppercase.isChecked) {
                characters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            }
            if (cbLowercase.isChecked) {
                characters += "abcdefghijklmnopqrstuvwxyz"
            }
            if (cbNumbers.isChecked) {
                characters += "0123456789"
            }
            if (cbSymbols.isChecked) {
                characters += "!@#$%^&*()_+-="
            }

            val password = StringBuilder()
            for (i in 0 until length) {
                val randomIndex = Random.nextInt(characters.length)
                password.append(characters[randomIndex])
            }

            tvPassword.text = password.toString()
            btnCopy.isEnabled = true
            Toast.makeText(this, "Senha gerada!", Toast.LENGTH_SHORT).show()
        }

        btnCopy.setOnClickListener {
            val password = tvPassword.text.toString()
            if (password.isNotEmpty() && password != "Clique em GERAR SENHA para comecar") {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("password", password)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Senha copiada!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
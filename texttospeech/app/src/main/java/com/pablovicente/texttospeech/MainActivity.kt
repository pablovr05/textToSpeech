package com.pablovicente.texttospeech

import android.app.Activity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class MainActivity : Activity() {
    private lateinit var t1: TextToSpeech
    private lateinit var ed1: EditText
    private lateinit var b1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ed1 = findViewById(R.id.editText)
        b1 = findViewById(R.id.button)
        t1 = TextToSpeech(applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val langResult = t1.setLanguage(Locale.JAPAN)
                if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(applicationContext, "Idioma no soportado o datos faltantes", Toast.LENGTH_SHORT).show()
                }
            } else {
                // En caso de que no se haya inicializado correctamente
                Toast.makeText(applicationContext, "Error al inicializar TTS", Toast.LENGTH_SHORT).show()
            }
        }

        b1.setOnClickListener {
            val toSpeak = ed1.text.toString()
            if (toSpeak.isNotEmpty()) {
                Toast.makeText(applicationContext, toSpeak, Toast.LENGTH_SHORT).show()
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                Toast.makeText(applicationContext, "Por favor, ingrese algún texto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        if (::t1.isInitialized) {
            t1.stop()
            t1.shutdown()
        }
        super.onPause()
    }
}

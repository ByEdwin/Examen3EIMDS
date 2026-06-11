package com.example.examen3

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvPlayerName = findViewById<TextView>(R.id.tvPlayerName)
        val tvLastConnection = findViewById<TextView>(R.id.tvLastConnection)

        val username = intent.getStringExtra("USERNAME") ?: "Invitado"
        tvPlayerName.text = username

        val db = AppDatabase.getDatabase(this)
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val currentTime = sdf.format(Date())

        lifecycleScope.launch {
            val user = db.userDao().getUserByUsername(username)
            user?.let {
                // Mostrar la conexión anterior si existe
                it.lastConnection?.let { last ->
                    tvLastConnection.text = getString(R.string.last_connection_label) + " " + last
                }
                
                // Actualizar con la fecha actual para la próxima vez
                db.userDao().updateLastConnection(it.id, currentTime)
            }
        }
    }
}
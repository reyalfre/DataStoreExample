package com.alfredo.datastoreexample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "USER_PREFERENCES_NAME")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val etName = findViewById<EditText>(R.id.etName)
        val cbVIP = findViewById<CheckBox>(R.id.cbVIP)

        btnSave.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                saveValues(
                    etName.text.toString(),
                    cbVIP.isChecked
                )
            }
            startActivity(Intent(this, DetailActivity::class.java))

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private suspend fun saveValues(name: String, checked: Boolean) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey("name")] = name
            preferences[booleanPreferencesKey("vip")] = checked
        }
    }
}
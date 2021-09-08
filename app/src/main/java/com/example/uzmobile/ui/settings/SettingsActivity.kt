package com.example.uzmobile.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uzmobile.MainActivity
import com.example.uzmobile.R
import com.example.uzmobile.databinding.ActivitySettingsBinding
import com.example.uzmobile.language.LocaleHelper

class SettingsActivity : AppCompatActivity() {
    lateinit var binding:ActivitySettingsBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("Language",0)
        binding.apply {
            uz.setOnClickListener {
                val edit = sharedPreferences.edit()
                edit.putString("lan","uz")
                LocaleHelper.setLocale(this@SettingsActivity,"uz")
                edit.apply()
                var intent = Intent(this@SettingsActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            ru.setOnClickListener {
                val edit = sharedPreferences.edit()
                edit.putString("lan","ru")
                LocaleHelper.setLocale(this@SettingsActivity,"ru")
                edit.apply()
                var intent = Intent(this@SettingsActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            cril.setOnClickListener {
                val edit = sharedPreferences.edit()
                edit.putString("lan","kril")
                LocaleHelper.setLocale(this@SettingsActivity,"en")
                edit.apply()
                var intent = Intent(this@SettingsActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        var intent = Intent(this@SettingsActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
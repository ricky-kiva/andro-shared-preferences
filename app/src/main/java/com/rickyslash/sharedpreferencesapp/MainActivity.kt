package com.rickyslash.sharedpreferencesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

// Shared Preferences: data saving mechanism that supports 'key-value' structure
// - shared preferences is atomic: every type of data can stand by itself

// EncryptedSharedPreferences: same, but with encryption. Good for saving confidential data
package com.uniandes.vinilosapplication.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.uniandes.vinilosapplication.MainActivity
import com.uniandes.vinilosapplication.R

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
    }

    fun onClick(v: View) {
        if(v?.id == R.id.bt_start) {
            startActivity(Intent(this, MainActivity::class.java))
            finish();
        }
    }
}
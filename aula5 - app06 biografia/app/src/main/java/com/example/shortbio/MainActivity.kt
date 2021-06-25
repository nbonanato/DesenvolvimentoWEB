package com.example.shortbio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button


class MainActivity : AppCompatActivity() {

    private lateinit var btnSobre: Button
    private lateinit var btnForm: Button
    private lateinit var btnExp: Button
    private lateinit var btnObj: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSobre = findViewById(R.id.btnSobre)
        btnExp = findViewById(R.id.btnExp)
        btnForm = findViewById(R.id.btnForm)
        btnObj = findViewById(R.id.btnObjetivo)

        btnSobre.setOnClickListener{
            setContentView(R.layout.activity_second)
        }

        btnForm.setOnClickListener{
            setContentView(R.layout.activity_third)
        }

        btnExp.setOnClickListener{
            setContentView(R.layout.activity_fourth)
        }

        btnObj.setOnClickListener{
            setContentView(R.layout.activity_fifth)
        }

    }
    override fun onStart() {
        super.onStart()
        Log.d("CICLOVIDA", "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("CICLOVIDA", "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("CICLOVIDA", "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CICLOVIDA", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CICLOVIDA", "OnDestroy")
    }
}
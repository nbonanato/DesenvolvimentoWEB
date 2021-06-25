package com.example.app05_cadastro

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    private lateinit var foto: ImageView
    private lateinit var botaoTirarFoto: Button
    private lateinit var nome: EditText
    private lateinit var idade: EditText
    private lateinit var botaoSalvar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foto = findViewById(R.id.imageView2)
        botaoTirarFoto = findViewById(R.id.button3)
        nome = findViewById(R.id.editTextTextPersonName2)
        idade = findViewById(R.id.editTextNumber2)
        botaoSalvar = findViewById(R.id.button4)

        botaoSalvar.setOnClickListener {
            //INTENT EXPLICITA
            val intent = Intent(this@MainActivity, MainActivity2::class.java)

            val usuario = Usuario(nome = nome.text.toString(), idade = idade.text.toString().toInt())
            intent.putExtra("USUARIO", usuario)

            startActivity(intent)
        }

        botaoTirarFoto.setOnClickListener {
            abrirCamera()
        }
    }

    fun abrirCamera() {
        //INTENT IMPLÍCITA
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 12345)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12345 && resultCode == RESULT_OK) {
            val fotoTirada = data?.extras?.get("data") as Bitmap
            foto.setImageBitmap(fotoTirada)
        }
    }

    override fun onBackPressed() {
        var dialog = AlertDialog.Builder(this@MainActivity)
        dialog.setTitle("Confirmação")
        dialog.setPositiveButton("Sim", DialogInterface.OnClickListener { dialogInterface, i -> super.onBackPressed() })
        dialog.create().show()
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

package com.jvhp.app11_listapetsfotocadastro

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CadastroActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener{
    lateinit var raca: Raca
    lateinit var edtNome: EditText
    lateinit var edtIdade: EditText
    lateinit var btnSalvar: Button
    lateinit var imvFoto: ImageView
    var fotoTirada: Bitmap?=null
    lateinit var swtCastrado: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        edtNome = findViewById(R.id.edtNome)
        edtIdade = findViewById(R.id.edtIdade)
        btnSalvar = findViewById(R.id.btnSalvar)
        imvFoto = findViewById(R.id.imvFoto)
        swtCastrado = findViewById(R.id.swtCastrado)

        imvFoto.setOnClickListener() {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null)
                startActivityForResult(intent, RC_CAMERA)
        }

        btnSalvar.setOnClickListener(){
            val pet = Pet(fotoTirada, edtNome.text.toString(),
            edtIdade.text.toString(), raca, swtCastrado.isChecked)
            MainActivity.lista.add(pet)
            finish()
        }

    }

    fun onStackClick(view: View){
        if (view is RadioButton){
            var checked = view.isChecked

            when (view.id){
                R.id.rdbCachorro -> { if (checked) {raca = Raca.CACHORRO}}
                R.id.rdbGato -> { if (checked) {raca = Raca.GATO}}
                R.id.rdbJabuti -> { if (checked) {raca = Raca.JABUTI}}
            }
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if((resultCode == RESULT_OK) && (requestCode == RC_CAMERA)){
            fotoTirada = data?.extras?.get("data") as Bitmap
            imvFoto.setImageBitmap(fotoTirada)
        }
    }

    companion object{
        const val RC_CAMERA  = 12345
        const val RC_GALERIA = 67890
    }

}
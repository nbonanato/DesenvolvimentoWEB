package com.jvhp.app19_cep_r_sp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.jvhp.app19_cep_r_sp.data.model.CEP
import com.jvhp.app19_cep_r_sp.domain.CEPService
import com.jvhp.app19_cep_r_sp.util.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var campoCEP: EditText
    private lateinit var botaoCEP: Button
    private lateinit var respostaCEP: TextView
    private lateinit var carregamentoCEP: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        campoCEP = findViewById(R.id.edtCEP)
        botaoCEP = findViewById(R.id.btnBuscarCEP)
        respostaCEP = findViewById(R.id.txtCEPResponse)
        carregamentoCEP = findViewById(R.id.prgLoading)

        configureCEPMask()

        botaoCEP.setOnClickListener{
            val cep = campoCEP.text.toString()
            if(cep.isNotEmpty()){
                buscarCEP(cep)
            } else {
                campoCEP.error = "Digite um CEP válido!!"
            }
        }
    }

    fun buscarCEP(cep: String){
        val retrofitClient = Network.retrofitConfig("https://viacep.com.br/ws/")
        val servico = retrofitClient.create(CEPService::class.java)

        CoroutineScope(IO).launch {
            try {
                val response = servico.buscarCEP(cep.replace("-", ""))

                withContext(Main){
                    changeLoadingVisibility(isVisible = true)
                    delay(2000L)
                    if(response.isSuccessful){
                        changeLoadingVisibility(isVisible = false)
                        respostaCEP.text = response.body().toString()
                    }
                }
            }catch (e: Exception){
                withContext(Main){
                    changeLoadingVisibility(isVisible = false)
                    respostaCEP.text = "Não foi possível processar a sua solicitação."
                }
            }
        }
    }

    fun configureCEPMask(){
        campoCEP.addTextChangedListener(object : TextWatcher{
            var isUpdating: Boolean = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString().replace("-", "")
                var mascara = ""
                val mask = "#####-###"
                if(isUpdating){
                    isUpdating = false
                    return
                }
                var i = 0
                for(m in mask.toCharArray()) {
                    if(m != '#' && count > before) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch(e: Exception) {
                        break
                    }
                    i++
                }
                isUpdating = true
                campoCEP.setText(mascara)
                campoCEP.setSelection(mascara.length)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    fun changeLoadingVisibility(isVisible: Boolean){
        if(isVisible){
            respostaCEP.text = ""
            carregamentoCEP.visibility = View.VISIBLE
            botaoCEP.visibility = View.INVISIBLE
        } else {
            carregamentoCEP.visibility = View.INVISIBLE
            botaoCEP.visibility = View.VISIBLE
        }
    }

}
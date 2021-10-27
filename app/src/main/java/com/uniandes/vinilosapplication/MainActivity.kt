package com.uniandes.vinilosapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.uniandes.vinilosapplication.broker.RetrofitBroker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getButton: Button = findViewById(R.id.fetch_button)
        val getResultTextView : TextView = findViewById(R.id.get_result_text)
        getButton.setOnClickListener {
            RetrofitBroker.getRequest(onResponse = {
                getResultTextView.text = it
            }, onFailure = {
                getResultTextView.text = it
            })
        }

        val postButton: Button = findViewById(R.id.post_button)
        val postResultTextView : TextView = findViewById(R.id.post_result_text)
        postButton.setOnClickListener {
            val mailTxt : TextInputEditText = findViewById(R.id.txt_post_mail)
            val nameTxt : TextInputEditText = findViewById(R.id.txt_post_name)
            val phoneTxt : TextInputEditText = findViewById(R.id.txt_post_phone)
            val postParams = mapOf<String, String>(
                "name" to nameTxt.text.toString(),
                "telephone" to phoneTxt.text.toString(),
                "email" to mailTxt.text.toString()
            )
            RetrofitBroker.postRequest(postParams,
                onResponse = {
                    postResultTextView.text = it
                }, onFailure = {
                    postResultTextView.text = it
                })
        }

        val putButton: Button = findViewById(R.id.put_button)
        val putResultTextView : TextView = findViewById(R.id.put_result_text)
        putButton.setOnClickListener {
            val idTxt : TextInputEditText = findViewById(R.id.txt_put_id)
            val mailTxt : TextInputEditText = findViewById(R.id.txt_put_mail)
            val nameTxt : TextInputEditText = findViewById(R.id.txt_put_name)
            val phoneTxt : TextInputEditText = findViewById(R.id.txt_put_phone)
            val putParams = mapOf<String, String>(
                "id" to idTxt.text.toString(),
                "name" to nameTxt.text.toString(),
                "telephone" to phoneTxt.text.toString(),
                "email" to mailTxt.text.toString()
            )
            RetrofitBroker.putRequest(putParams,
                onResponse = {
                    putResultTextView.text = it
                }, onFailure = {
                    putResultTextView.text = it
                })
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        supportActionBar!!.title = "Retrofit"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                // Create an intent with a destination of the other Activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
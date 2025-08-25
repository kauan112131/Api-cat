package com.example.gatos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var catImageView: ImageView
    private lateinit var loadCatButton: Button
    private lateinit var searchBreedButton: Button
    private lateinit var breedInput: EditText

    private val apiKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        catImageView = findViewById(R.id.catImageView)
        loadCatButton = findViewById(R.id.loadCatButton)
        searchBreedButton = findViewById(R.id.searchBreedButton)
        breedInput = findViewById(R.id.breedInput)


        loadCatButton.setOnClickListener {
            loadRandomCat()
        }


        searchBreedButton.setOnClickListener {
            val breedId = breedInput.text.toString().trim()
            if (breedId.isNotEmpty()) {
                loadCatByBreed(breedId)
            } else {
                Toast.makeText(this, "Digite um ID de raça", Toast.LENGTH_SHORT).show()
            }
        }


        loadRandomCat()
    }

    private fun loadRandomCat() {
        lifecycleScope.launch {
            try {
                val cats = ApiClient.instance.getRandomCat() // suspend
                val catUrl = cats.firstOrNull()?.url
                if (!catUrl.isNullOrEmpty()) {
                    Picasso.get().load(catUrl).into(catImageView)
                    Toast.makeText(this@MainActivity, "A random cute cat 😺", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Nenhum gato encontrado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Erro ao carregar gato: ${e.localizedMessage ?: e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadCatByBreed(breedId: String) {
        lifecycleScope.launch {
            try {
                val cats = ApiClient.instance.getCatByBreed(breedId, apiKey) // suspend
                val catUrl = cats.firstOrNull()?.url
                if (!catUrl.isNullOrEmpty()) {
                    Picasso.get().load(catUrl).into(catImageView)
                    Toast.makeText(this@MainActivity, "Gato da raça: $breedId", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Nenhum gato encontrado para esta raça", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Erro ao buscar raça: ${e.localizedMessage ?: e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

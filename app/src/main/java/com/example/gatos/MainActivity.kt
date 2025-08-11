package com.example.gatos

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gatos.ApiClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var catImageView: ImageView
    private lateinit var loadCatButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catImageView = findViewById(R.id.catImageView)
        loadCatButton = findViewById(R.id.loadCatButton)

        loadCatButton.setOnClickListener {
            loadRandomCat()
        }

        loadRandomCat()
    }

    private fun loadRandomCat() {
        val call = ApiClient.instance.getRandomCat()
        call.enqueue(object : Callback<List<CatUrlResponse>> {
            override fun onResponse(
                call: Call<List<CatUrlResponse>>,
                response: Response<List<CatUrlResponse>>
            ) {
                if (response.isSuccessful) {
                    val catUrl = response.body()?.firstOrNull()?.url
                    if (catUrl != null) {
                        val cat = CatResponse(
                            url = catUrl,
                            message = "A random cute cat 😺"
                        )
                        Picasso.get().load(cat.url).into(catImageView)
                        Toast.makeText(this@MainActivity, cat.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Erro na resposta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CatUrlResponse>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

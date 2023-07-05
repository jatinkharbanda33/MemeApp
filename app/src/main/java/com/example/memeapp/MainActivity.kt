package com.example.memeapp
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    var currentimgurl =""
    private fun loadmeme() {
        findViewById<ProgressBar>(R.id.progbar).visibility=View.VISIBLE

        val px = "https://meme-api.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, px, null,
            Response.Listener { response ->
                val url=response.getString("url")
                currentimgurl= url
                Glide.with(this).load(url).listener(object :RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progbar).visibility=View.GONE
                        return false;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        findViewById<ProgressBar>(R.id.progbar).visibility=View.GONE
                        return false;
                    }
                }).into(findViewById(R.id.memeimage))

            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()

                // TODO: Handle error
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    fun nextfun(view: View) {
        loadmeme()

    }
    fun sharefun(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this cool meme from reddit $currentimgurl")
        val chooser=Intent.createChooser(intent,"Share this meme through")
        startActivity(chooser)


    }
}
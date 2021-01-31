package com.example.currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val URL="https://api.coindesk.com/v1/bpi/currentprice.json"
    var okHttpClient: OkHttpClient = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.load->{
                loadBitcoinPrice()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    private fun loadBitcoinPrice(){
        runOnUiThread {
            progressBar.visibility=View.VISIBLE
        }
        val request: Request =Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val json=response?.body()?.string()
                val usdRate=(JSONObject(json).getJSONObject("bpi").getJSONObject("USD")["rate"] as String).split(".")[0]
                val eurRate=(JSONObject(json).getJSONObject("bpi").getJSONObject("EUR")["rate"] as String).split(".")[0]
                runOnUiThread {
                    progressBar.visibility=View.GONE
                    bitcoinValues.text="$: $usdRate\n\n€: $eurRate"
                }


            }
        })
    }
}
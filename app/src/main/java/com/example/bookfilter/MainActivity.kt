package com.example.bookfilter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authorInput = findViewById<TextInputLayout>(R.id.AuthorInput)
        val country = findViewById<TextInputLayout>(R.id.Country)
        val dataCount = findViewById<TextView>(R.id.resultOne)
        val dataResultTwo = findViewById<TextView>(R.id.resulttwo)
        val dataResultThree = findViewById<TextView>(R.id.resultThree)
        val dataResultFour=findViewById<TextView>(R.id.resultFour)
        val filterButton = findViewById<Button>(R.id.button)
        val titles = mutableListOf<String>()
        filterButton.setOnClickListener {
            titles.clear()
            dataCount.text =""
            dataResultTwo.text =""
            dataResultThree.text =""

            val myApplication=application as MyApplication
            val httpApiService=myApplication.httpApiService
            CoroutineScope(Dispatchers.Default).launch {
                val decodedJsonResult = httpApiService.getMyBookData()

                for (item in decodedJsonResult) {
                    if (item.author.lowercase() == authorInput.editText?.text.toString()
                            .lowercase() && item.country.lowercase() == country.editText?.text.toString()
                            .lowercase()
                    ) {
                        titles.add(item.title)

                    }

                }
                withContext(Dispatchers.Main) {
                    dataCount.text = "Result: " + titles.count().toString()
                    if (titles.count() > 0)
                        dataResultTwo.text = "Result: " + titles[0]
                    if (titles.count() > 1)
                        dataResultThree.text = "Result: " + titles[1]
                    if (titles.count() > 2)
                        dataResultFour.text = "Result: " + titles[2]
                }


            }


        }
    }
}
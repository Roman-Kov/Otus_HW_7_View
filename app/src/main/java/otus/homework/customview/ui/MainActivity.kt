package otus.homework.customview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.homework.customview.R
import otus.homework.customview.data.DataProvider

class MainActivity : AppCompatActivity() {

    private val dataProvider = DataProvider(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = dataProvider.getExpenses()
    }
}
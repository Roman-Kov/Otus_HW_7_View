package otus.homework.customview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.homework.customview.R
import otus.homework.customview.data.DataProvider

class MainActivity : AppCompatActivity() {

    val dataProvider = DataProvider(this)
    val navigator = Navigator()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.navigateToPieChart()
    }

    inner class Navigator {

        fun navigateToPieChart() {
            val pieChartFragment = PieChartFragment()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainer, pieChartFragment)
                commit()
            }
        }

        fun navigateToBarChart(category: String) {
            val barChartFragment = BarChartFragment.createInstance(category)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, barChartFragment)
                addToBackStack("Main")
                commit()
            }
        }
    }
}
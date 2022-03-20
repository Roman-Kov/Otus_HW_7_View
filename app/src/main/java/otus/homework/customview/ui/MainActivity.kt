package otus.homework.customview.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import otus.homework.customview.R
import otus.homework.customview.data.DataProvider
import otus.homework.customview.data.Expense
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val dataProvider = DataProvider(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = dataProvider.getExpenses()
        val pieChart = findViewById<PieChartView>(R.id.pieChart)
        pieChart.drawChart(
            dataToPieChartData(data)
        )
    }

    private fun dataToPieChartData(expenses: List<Expense>): List<Pair<Float, Int>> {
        val amounts = expenses.map { it.amount }
        val sum = amounts.sum()
        if (sum == 0) return listOf()
        val valueInDegree: Float = sum.toFloat() / 360
        return amounts.map { amount ->
            Pair(
                (amount / valueInDegree),
                getRandomColor()
            )
        }
    }

    private fun getRandomColor(): Int {
        return Color.argb(255, Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256))
    }
}
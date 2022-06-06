package otus.homework.customview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import otus.homework.customview.R
import otus.homework.customview.data.Expense
import otus.homework.customview.data.PieChartParam
import otus.homework.customview.util.getRandomColor

class PieChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pie_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChart()
    }

    private fun initChart() {
        val data = (activity as MainActivity).dataProvider.getExpenses()
        val pieChart = view?.findViewById<PieChartView>(R.id.pieChart)
        pieChart?.apply {
            drawChart(dataToPieChartData(data))
            setOnClickListener { category ->
                animate()
                    .setDuration(1000)
                    .rotation(360f)
                    .setInterpolator(OvershootInterpolator())
                    .withEndAction {
                        (activity as MainActivity).navigator.navigateToBarChart(category)
                    }
                    .start()
            }
        }
    }

    private fun dataToPieChartData(expenses: List<Expense>): List<PieChartParam> {
        val amountsByCategory = expenses.groupBy {
            it.category
        }.map {
            Pair(
                it.key,
                it.value.sumOf {
                    it.amount
                }
            )
        }
        val sum = amountsByCategory.sumOf { it.second }
        if (sum == 0) return listOf()
        val valueInDegree: Float = sum.toFloat() / 360
        return amountsByCategory.map {
            PieChartParam(
                it.first,
                (it.second / valueInDegree),
                getRandomColor()
            )
        }
    }
}